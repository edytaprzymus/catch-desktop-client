package connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.simple.parser.ParseException;
import connector.Database;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.HttpResponseException;

public class Synchronizer {

//	private miniStreamList streamsFromServer;
//	private miniStreamList streamsFromDb;
	List<miniStream> streamListSv = new ArrayList<miniStream>();
	List<catchObject> objectListSv = new ArrayList<catchObject>();
	List<miniStream> streamListDb = new ArrayList<miniStream>();
	List<catchObject> objectListDb = new ArrayList<catchObject>();
	
	
	
	/**
	 * Metoda rozpoczynajaca synchronizacje. Moze byc odpalona tylko i wylacznie po zalogowaniu i przy dostepie do internetu
	 * @param connector Obiekt niezbedny do wykonywania operacji na serwerze
	 * @param after_login Sprawdzam czy synchronizacja uruchomiona zostala automatycznie po logowaniu (true),
	 * czy po nacisnieciu przycisku (false)
	 * @throws NotCompletedException wyjatek rzucany gdyby w trakcie synchronizacji zostala ona przerwana
	 */
	public void run(catchConnector connector,boolean after_login) {
	

		
		//Sprawdzam czy synchronizacja zostala uruchomiona automatycznie po zalogowaniu
		if(after_login) {
			//Sprawdzam czy zalogowany uzytkownik jest wlascicielem aktualnie posiadanej bazy
			boolean newUser = checkUserInDb(connector.getEncodedLoginData());
			
			if(newUser) { //Jezeli zalogowany uzytkownik jest nowo zalogowanym uzytkownikiem (nie jest wlascicielem aktualnej bazy)
				System.out.println("[SYNCHRO]Nowy uzytkownik. Czyszcze baze i synchronizuje dane z serwera");
				Database.dropDb();  //Czyszcze cala baze
                                Database.createNewUser(connector); //dodaje do bazy wpis o uztkowniku
				getDataFromServer(connector,true); //Pobieram i tworze baze na nowo
				return;
			}
		}
		run(connector);
	}
	
	/**
	 * Metoda synchronizujaca dane znajdujace sie na serwerze z tymi znajdujacymi sie w bazie
	 * @param connector
	 * @throws NotCompletedException
	 */
	public void run(catchConnector connector) {
		
		getDataFromDb();
		getDataFromServer(connector,false);
		synchronize(connector);
		
	}

	/**
	 * Wlasciwa synchronizacja
	 */
	private void synchronize(catchConnector connector) {
		
		for(miniStream streamDb : streamListDb) {
			Long deletedLocally = new Long(streamDb.getDeleted_locally()==null?"0":streamDb.getDeleted_locally());
			  boolean deleted = true; //zmienna mowiaca czy stream zostal usuniety na serwerze
			
			if(deletedLocally>0) {
				System.out.println("[SYNCHRO]Stream: "+ streamDb.getName() + " usuniety lokalnie usuwam na serwerze");
				
				try {
					connector.deleteStream(streamDb.getId(), streamDb.getServer_modified_at());
                                        StreamFromLocal streamFromLocal = new StreamFromLocal();
					streamFromLocal.deleteStream(streamDb.getId());
				} catch (UnsupportedEncodingException e) {
					System.err.println("[SYNCHRO]Nie mozna usunac streamu: "+ streamDb.getName());
					e.printStackTrace();
				} catch (IOException e) {
					System.err.println("[SYNCHRO]Nie mozna usunac streamu: "+ streamDb.getName());
					e.printStackTrace();
				} catch(Exception e) {
					System.err.println("[SYNCHRO]Nie mozna usunac streamu z bazy: "+ streamDb.getName());
					e.printStackTrace();
				} finally {
                                    System.out.println("[SYNCHRO]Usunieto stream: "+ streamDb.getName());
                                    continue;
                                }
				
				
			}
			
			
			if(streamDb.getId().contains("local")) { //Czy streamy zostaly utworzone z poziomu programu i nie zsynchronizowane
				System.out.println("[SYNCHRO]Stream: "+ streamDb.getName() + " utworzony lokalnie, tworze na serwerze");
				try {
				    HttpResponse resp =	connector.createStream(streamDb.getName());
				} catch(Exception e) {
					System.err.println("[SYNCHRO]Nie mozna utworzyc streamu: "+ streamDb.getName() + " na serwerze");
					e.printStackTrace();
				}
				 
				
				for(catchObject cO : objectListDb) {
					if(Database.getStreamObject(streamDb.getId()).contains(cO.getId())) {
						String[] table = new String[1];
						for(int i=0;i<cO.getTags().size();i++) {
							table[i] = cO.getTags().get(i);
						}
						try {
							connector.createNewNote(streamDb.getId(), cO.getText(), table);
						} catch (UnsupportedEncodingException e) {
							System.err.println("[SYNCHRO]Nie mozna utworzyc notatki");
							e.printStackTrace();
						} catch (IOException e) {
							System.err.println("[SYNCHRO]Nie mozna utworzyc notatki");
							e.printStackTrace();
						}
					}
					//Usuwam na koniec obiekt z bazy zeby pobrac z parametrami z servera
						ObjectFromLocal ofl = new ObjectFromLocal();
						ofl.deleteObject(cO.getId());
							
							
				}
                                StreamFromLocal sfl = new StreamFromLocal();
                                sfl.deleteStream(streamDb.getId());
			
			} else { //Stream byl juz synchronizowany
				for(miniStream streamSv : streamListSv) {
					if(streamDb.getId().equals(streamSv.getId())) { //Szukam tych samych streamow
						Long modifiedDb = new Long(streamDb.getServer_modified_at()==null?"0":streamDb.getServer_modified_at());
						Long modifiedSv = new Long(streamSv.getServer_modified_at()==null?"0":streamSv.getServer_modified_at());
						Long modifiedLocal = new Long(streamDb.getModified_locally()==null?"0":streamDb.getModified_locally());
						if(modifiedSv>modifiedDb) { //Stream na serwerze nowszy, nadpisuje rowniez nie zsynchronizowane streamy z bazy
                                                    System.out.println("[SYNCHRO]Stream na serwerze nowszy");
							try {
								//dodanie streama do bazy, usuwam na koniec powinno go dodac
								StreamFromLocal sfl = new StreamFromLocal();
								sfl.deleteStream(streamSv.getId());
								
								
						     
							
								    
							} catch (Exception e) {
								System.err.println("[SYNCHRO]Nie mozna usunac streama z bazy (nadpisywanie nowszym z serwera)");
								e.printStackTrace();
							
							}
							
							
							
							
						} else if(modifiedSv<modifiedLocal) { //Stream w bazie nowszy
							
                                                try {
                                                    connector.editStream(streamDb.getId(), streamDb.getServer_modified_at(), streamDb.getName(), Database.getColorForStream(streamDb.getId()));
                                                } catch (UnsupportedEncodingException ex) {
                                                    System.err.println("[SYNCHRO]Blad przy edycji streama na serwerze");
                                                } catch (IOException ex) {
                                                    System.err.println("[SYNCHRO]Blad przy edycji streama na serwerze");
                                                }
//							
							System.out.println("[SYNCHRO]Aktualizuje streama na serwerze");
						} //W innych przypadkach nie robimy nic bo streamy sa zsynchronizowane
						deleted = false;
						break;
					}
					
				}
				if(deleted) {
					StreamFromLocal sfl = new StreamFromLocal();
					sfl.deleteStream(streamDb.getId());
						
					System.out.println("Usuniety z bazy bo byl usuniety na serwerze");
				}
				
				
						
			}
		}
		getDataFromServer(connector,true);
		
	}

	/**
	 * Metoda pobierajaca wszystkie dane z serwera Catcha
	 * @param connector
	 * @return 
	 */
	public List<miniStream> getDataFromServer(catchConnector connector2, Boolean addToDb) {
		
		HttpResponse response = null;
                catchConnector connector = connector2;
		try {
//                     try {//musze zczytac odpowiedz ktora nie zostala do konca przetworzona
//                        connector.getResponseStatus(response);
//                     } catch (ParseException ex) {
//                        System.err.println("Blad przy logowaniu");
//                     }
                     try {
                        response = connector.getStreams();
                     } catch(IllegalStateException e) {
                        System.out.println("[SYNCHRO]Jakis blad, lista streamow nie pobrana. Zsynchronizuj jeszcze raz");
                        return null;
                     }
                     
			streamListSv = new miniStreamList(response).getStreams();
			System.out.println("Ilosc streamow na serwerze: "+streamListSv.size());
			for(miniStream mS : streamListSv) {
          
				//StreamController streamController = new StreamController(mS);
				response = connector.getStream(mS.getId());
				catchStream cStream = new catchStream(response);
				ObjectController objectController = new ObjectController(cStream,connector);
				objectListSv = objectController.getCatchObjectsList();
				
				if(addToDb) {
					objectController.addObjectToDataBase();
                                        
					StreamController streamController = new StreamController(mS,connector);
					streamController.addStreamToDataBase();
                                        objectController.addObjectsInStreamsToDatabase();
				}	
				
			}
		
		
		} catch (ParseException e) {
			System.err.println("[SYNCHRO]Nie mozna pobrac danych z serwera");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("[SYNCHRO]Nie mozna pobrac danych z serwera");
			e.printStackTrace();
		} 
		
		return streamListSv;
		
		
	}



	/**
	 * Metoda sprawdzajaca czy zalogowany uzytkownik jest wlascicielem aktualnie posiadanej bazy false- wlasciciel, true- nowy uzytkownik 
	 * @param encodedLoginData
	 * @return
	 */
	private boolean checkUserInDb(String encodedLoginData) {
		
		return Database.checkUser(encodedLoginData);
	}

	private List<miniStream> getDataFromDb() {
	
		StreamFromLocal streamFromLocal = new StreamFromLocal();
		ObjectFromLocal objectFromLocal = new ObjectFromLocal();
		streamListDb = streamFromLocal.getStreams();
		System.out.println("Ilosc streamow w bazie"+streamListDb.size());
		for(miniObject mO : objectFromLocal.getObjects()) {
			objectListDb.add(objectFromLocal.getObject(mO.getId()));
			
		}
		
		return streamListDb;
	}
	
	
	
}
