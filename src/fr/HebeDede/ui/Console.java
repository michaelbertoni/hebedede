package fr.HebeDede.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import fr.HebeDede.data.DatabaseConnection;
import fr.HebeDede.exception.UtilisateurInconnuException;
import fr.HebeDede.model.Article;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.model.Option;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.ArticleDAO;
import fr.HebeDede.repositories.BandedessineeDAO;
import fr.HebeDede.repositories.FigurineDAO;
import fr.HebeDede.repositories.OptionDAO;
import fr.HebeDede.repositories.UtilisateurDAO;
import fr.HebeDede.service.AuthentificationService;

public class Console {
	
	public static Utilisateur user;
	public static Scanner sc = new Scanner(System.in);
	public static Long dureeResa = (long) (4*60*60*1000);
	
	public static void promptLogin() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		AuthentificationService authentificationService = new AuthentificationService();
		
		String username = null;
		String password = null;
		
		Boolean userConnected = false;
		while(userConnected != true) {
			try {
				sc.nextLine();
				
				affiche("Login :");
				username = sc.nextLine();
				
				affiche("Password :");
				password = sc.nextLine();
				
				userConnected = authentificationService.login(username, password);
				if (userConnected != true) {
					affiche("Mot de passe incorrect ! Réessayez.");
				}
			} catch (UtilisateurInconnuException e) { }
		}
		UtilisateurDAO userDAO = new UtilisateurDAO();
		user = userDAO.findByUsername(username);
		
		affiche("Vous êtes connectés !");
		
		promptMenu();
	}

	public static void promptMenu() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		affiche("\n******* Site Web d'HébéDédé *******\n\nMenu principal :");
		
		affiche("1. Recherche/Liste des articles\n");
		if (user == null) {
			affiche("2. Login");
			affiche("3. Créer un compte");
		}
		else {
			switch (user.getRole()){
			case "Employe" :
				affiche("2. Liste des options");
				affiche("3. Mon compte");
				break;
			case "Chef" :
				affiche("2. Liste des options");
				affiche("3. Mon compte");
				affiche("4. Gérer comptes utilisateurs");
				break;
			case "Client" :
				affiche("2. Mes options");
				affiche("3. Mon compte");
				break;
			}
		}
		affiche("0. Quitter l'application");
		chooseMenu();
	}
		
	public static void chooseMenu() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException { 
	    Boolean choixCorrect = false;
	    int choice = 0;
	    while (choixCorrect == false) {
	    	affiche("\nVeuillez renseigner votre choix");
	    	choice = sc.nextInt();
	    	if (choice < 0 && choice > 4) {
	    		affiche("\nChoix incorrect.");
	    	} else {
	    		choixCorrect = true;
	    	}
	    }
	    selectMenu(choice);
	}
	
	public static void selectMenu(int choice) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
	    if (user == null) {
	    	switch (choice) {
		    	case 1:
		    		promptArticleList();
		    		break;
		    	case 2:
					promptLogin();
					break;
		    	case 3:
		    		promptCreerCompte();
		    		break;
		    	case 4:
		    		affiche("\nChoix incorrect.");
					chooseMenu();
					break;
		    	case 0:
	    			closeApp();
	    			break;
	    		default:
	    			affiche("\nChoix incorrect.");
					chooseMenu();
					break;
	    	}
	    } else {
			switch (choice) {
		    	case 1:
		    		promptArticleList();
		    		break;
		    	case 2:
		    		switch (user.getRole()) {
		    			case "Employe":
		    				promptOptionsList();
		    				break;
		    			case "Chef":
		    				promptOptionsList();
		    				break;
		    			case "Client":
		    				promptOptionsList();
		    				break;
		    		}
		    		break;
		    	case 3:
		    		switch (user.getRole()) {
		    			case "Employe":
		    				promptMonCompte();
		    				break;
		    			case "Chef":
		    				promptMonCompte();
		    				break;
		    			case "Client":
		    				promptMonCompte();
		    				break;
		    		}
		    		break;
	    		case 4:
	    			switch (user.getRole()) {
		    			case "Employe":
		    				affiche("\nChoix incorrect.");
		    				chooseMenu();
		    				break;
		    			case "Chef":
		    				promptGererComptesUtilisateurs();
		    				break;
		    			case "Client":
		    				affiche("\nChoix incorrect.");
		    				chooseMenu();
		    				break;
	    			}
	    			break;
	    		case 0:
	    			closeApp();
	    			break;
	    		default:
	    			affiche("\nChoix incorrect.");
					chooseMenu();
					break;
			}
	    }
	}
	
	public static void promptRechercheMultiCriteres() {
		// TODO Auto-generated method stub
	}

	public static void closeApp() {
		try {
			DatabaseConnection.getInstance().close();
			sc.close();
		} catch (ClassNotFoundException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public static void promptArticleList() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		BandedessineeDAO bdDAO = new BandedessineeDAO(); 
		FigurineDAO figDAO = new FigurineDAO();
		List<Bandedessinee> bdList = bdDAO.findAllBD();
		List<Figurine> figList = figDAO.findAllFig();
		affiche("\n******* Liste des Articles *******\n");
		for (Bandedessinee bd : bdList) {
			affiche("Bande dessinée - Réf. article " + bd.getIdArticle() + " | Titre : " + bd.getLibelle() + " | Collection : " + bd.getCollection()  + " | Prix : " + bd.getPrix() + "€" + " | Disponible : " + bd.dispo());
		}
		for (Figurine fig : figList) {
			affiche("Figurine - Réf. article " + fig.getIdArticle() + " | Description : " + fig.getDescription() + " | Prix : " + fig.getPrix() + "€" + " | Disponible : " + fig.dispo());
		}
		
		affiche("\nMenu :");
		if (user == null || user.getRole().equals("Client")) {
			affiche("1. Afficher les détails d'un article");
			affiche("2. Recherche multi-critères d'articles");
			affiche("0. Retour au menu principal");
		} else {
			affiche("1. Afficher les détails d'un article");
			affiche("2. Recherche multi-critères d'articles");
			affiche("3. Ajouter un article");
			affiche("0. Retour au menu principal");
		}
		chooseArticleList();
	}
	
	public static void chooseArticleList() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		Boolean choixCorrect = false;
	    int choice = 0;
	    while (choixCorrect == false) {
	    	affiche("\nVeuillez renseigner votre choix");
	    	choice = sc.nextInt();
	    	if (choice < 0 && choice > 3) {
	    		affiche("\nChoix incorrect.");
	    	} else {
	    		choixCorrect = true;
	    	}
	    }
	    selectArticleList(choice);
	}
	
	public static void selectArticleList(int choice) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		if (user == null || user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					ouvrirFicheArticle();
					break;
				case 2:
					promptRechercheMultiCriteres();
					break;
				case 3:
					affiche("\nChoix incorrect.");
					chooseArticleList();
					break;
				case 0:
					promptMenu();
					break;
				default:
					affiche("\nChoix incorrect.");
					chooseArticleList();
					break;
			}
		} else {
			switch (choice) {
				case 1:
					ouvrirFicheArticle();
					break;
				case 2:
					promptRechercheMultiCriteres();
					break;
				case 3:
					promptAjouterArticle();
					break;
				case 0:
					promptMenu();
					break;
				default:
					affiche("\nChoix incorrect.");
					chooseArticleList();
					break;
			}
		}
	}

	public static void ouvrirFicheArticle() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		affiche("Renseigner la référence de l'article à consulter :");
		Integer idArticle = 0;
		ArticleDAO artDAO = new ArticleDAO();
		FigurineDAO figDAO = new FigurineDAO();
		BandedessineeDAO bdDAO = new BandedessineeDAO();
		do {
			idArticle = sc.nextInt();
			if (!idArticle.equals(artDAO.find(idArticle).getIdArticle())) {
				affiche("La référence article est inexistante, veuillez réessayer.");
			}
		} while (!idArticle.equals(artDAO.find(idArticle).getIdArticle()));
		Figurine obj1 = figDAO.findByIdArticle(idArticle);
		Bandedessinee obj2 = bdDAO.findByIdArticle(idArticle);
		if (obj1 != null) {
			Figurine obj = obj1;
			promptFicheFigurine(obj);
		}
		else if (obj2 != null) {
			Bandedessinee obj = obj2;
			promptFicheBd(obj);
		}
	}

	public static void promptFicheBd(Bandedessinee bd) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		affiche("\n******* Fiche Bande déssinée *******\n\nRéf. article " + bd.getIdArticle());
		affiche("Titre : " + bd.getLibelle());
		affiche("Collection : " + bd.getCollection());
		affiche("Auteur : " + bd.getAuteur());
		affiche("Editeur : " + bd.getEditeur());
		affiche("Catégorie : " + bd.getCategorie());
		affiche("Nbr. pages : " + bd.getNbrPages());
		affiche("Description : " + bd.getDescription());
		affiche("\nEtat : " + bd.getEtat());
		affiche("Prix : " + bd.getPrix() + "€");
		affiche("Disponible en magasin : " + bd.dispo());
		
		affiche("\nMenu :");
		if (user == null){
			affiche("0. Retour à la liste des Articles");
		}
		else if (user.getRole().equals("Client")) {
			affiche("1. Réserver la BD");
			affiche("0. Retour à la liste des Articles");
		}
		else if (user.getRole().equals("Employe") || user.getRole().equals("Chef")) {
			affiche("1. Consulter les réservations de la BD");
			affiche("2. Modifier les informations de la BD");
			affiche("3. Supprimer la figurine du catalogue");
			affiche("0. Retour à la liste des Articles");
		}
		chooseFicheBd(bd);
	}

	public static void promptFicheFigurine(Figurine fig) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		affiche("\n******* Fiche Figurine *******\n\nRéf. article " + fig.getIdArticle());
		affiche("Description : " + fig.getDescription());
		affiche("Taille figurine : " + fig.getTaille());
		affiche("\nPrix : " + fig.getPrix() + "€");
		affiche("Disponible en magasin : " + fig.dispo());
		
		affiche("\nMenu :");
		if (user == null){
			affiche("0. Retour à la liste des Articles");
		}
		else if (user.getRole().equals("Client")) {
			affiche("1. Réserver la figurine");
			affiche("0. Retour à la liste des Articles");
		}
		else if (user.getRole().equals("Employe") || user.getRole().equals("Chef")) {
			affiche("1. Consulter les réservations de la figurine");
			affiche("2. Modifier les informations de la figurine");
			affiche("3. Supprimer la figurine du catalogue");
			affiche("0. Retour à la liste des Articles");
		}
		
		chooseFicheFigurine(fig);
	}

	public static void chooseFicheBd(Bandedessinee bd) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		Boolean choixCorrect = false;
	    int choice = 0;
	    while (choixCorrect == false) {
	    	affiche("\nVeuillez renseigner votre choix");
	    	choice = sc.nextInt();
	    	if (choice < 0 && choice > 4) {
	    		affiche("\nChoix incorrect.");
	    	} else {
	    		choixCorrect = true;
	    	}
	    }
	    selectFicheBd(choice, bd);
	}

	public static void chooseFicheFigurine(Figurine fig) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		Boolean choixCorrect = false;
	    int choice = 0;
	    while (choixCorrect == false) {
	    	affiche("\nVeuillez renseigner votre choix");
	    	choice = sc.nextInt();
	    	if (choice < 0 && choice > 4) {
	    		affiche("\nChoix incorrect.");
	    	} else {
	    		choixCorrect = true;
	    	}
	    }
	    selectFicheFigurine(choice, fig);
	}

	public static void selectFicheBd(int choice, Bandedessinee bd) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		if (user == null && choice == 0) {
			promptArticleList();
		}
		else if (user == null && choice != 0){
			affiche("\nChoix incorrect.");
			chooseFicheBd(bd);
		}
		else if (user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					promptReservation(bd);
					break;
				case 0:
					promptArticleList();
					break;
				default:
					affiche("\nChoix incorrect.");
					chooseFicheBd(bd);
					break;
			}
		}
		else if (user.getRole().equals("Employe") || user.getRole().equals("Chef")) {
			switch (choice) {
				case 1:
					promptConsultReservation(bd);
					break;
				case 2:
					modifFiche(bd);
					break;
				case 3:
					affiche("Êtes-vous sûr de vouloir supprimer cette BD (oui/non) ? ");
					String ok = "";
					do {
						ok = sc.nextLine();
						if (!ok.equals("oui") && !ok.equals("non")) {
							affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
						}
					} while (!ok.equals("oui") && !ok.equals("non"));
					if (ok.equals("oui")) { supprimerFiche(bd); }
					if (ok.equals("non")) { promptFicheBd(bd); }
					break;
				case 0:
					promptArticleList();
					break;
				default:
					affiche("\nChoix incorrect.");
					chooseFicheBd(bd);
					break;
			}
		}
		else {
			affiche("\nChoix incorrect.");
			chooseFicheBd(bd);
		}
	}

	public static void selectFicheFigurine(int choice, Figurine fig) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		if (user == null && choice == 0) {
			promptArticleList();
		}
		else if (user == null && choice != 0){
			affiche("\nChoix incorrect.");
			chooseFicheFigurine(fig);
		}
		else if (user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					promptReservation(fig);
					break;
				case 0:
					promptArticleList();
					break;
				default:
					affiche("\nChoix incorrect.");
					chooseFicheFigurine(fig);
					break;
			}
		}
		else if (user.getRole().equals("Employe") || user.getRole().equals("Chef")) {
			switch (choice) {
				case 1:
					promptConsultReservation(fig);
					break;
				case 2:
					modifFiche(fig);
					break;
				case 3:
					affiche("Êtes-vous sûr de vouloir supprimer cette figurine (oui/non) ? ");
					String ok = "";
					do {
						ok = sc.nextLine();
						if (!ok.equals("oui") && !ok.equals("non")) {
							affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
						}
					} while (!ok.equals("oui") && !ok.equals("non"));
					if (ok.equals("oui")) { supprimerFiche(fig); }
					if (ok.equals("non")) { promptFicheFigurine(fig); }
					break;
				case 0:
					promptArticleList();
					break;
				default:
					affiche("\nChoix incorrect.");
					chooseFicheFigurine(fig);
					break;
			}
		}
		else {
			affiche("\nChoix incorrect.");
			chooseFicheFigurine(fig);
		}
	}

	public static void supprimerFiche(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		String typeObj = obj.getClass().getSimpleName();
		if (typeObj.equals("Bandedessinee")) {
			Bandedessinee bd = (Bandedessinee) obj;
			ArticleDAO dao = new ArticleDAO();
			dao.delete(bd);
			affiche("Elément supprimé du catalogue, retour à la liste des Articles");
			Thread.sleep(3000);
			promptArticleList();
		} else if (typeObj.equals("Figurine")) {
			Figurine fig = (Figurine) obj;
			ArticleDAO dao = new ArticleDAO();
			dao.delete(fig);
			affiche("Elément supprimé du catalogue, retour à la liste des Articles");
			Thread.sleep(3000);
			promptArticleList();
		}
		else {
			affiche("Erreur à la suppression de l'élément. Retour à la liste des Articles");
			promptArticleList();
		}
	}

	public static void modifFiche(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		String typeObj = obj.getClass().getSimpleName();
		sc.nextLine();
		if (typeObj.equals("Bandedessinee")) {
			affiche("\nRenseigner les informations de la BD (Vous pouvez laisser les champs non modifiés vides) :\nTitre : ");
			Bandedessinee newBd = (Bandedessinee) obj;
			String libelle = sc.nextLine();
			if (!libelle.isEmpty()) { newBd.setLibelle(libelle); }
			affiche("Collection : ");
			String collection = sc.nextLine();
			if (!collection.isEmpty()) { newBd.setCollection(collection); }
			affiche("Auteur : ");
			String auteur = sc.nextLine();
			if (!collection.isEmpty()) { newBd.setAuteur(auteur); }
			affiche("Editeur : ");
			String editeur = sc.nextLine();
			if (!editeur.isEmpty()) { newBd.setEditeur(editeur); }
			affiche("Catégorie : ");
			String categorie = sc.nextLine();
			if (!categorie.isEmpty()) { newBd.setCategorie(categorie); }
			affiche("Nbr. pages : ");
			Integer nbPages = 0;
			do {
				nbPages = sc.nextInt();
				if (nbPages < 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			} while (nbPages < 0);
			if (nbPages > 0) { newBd.setNbrPages(nbPages); }
			affiche("Description : ");
			sc.nextLine();
			String desc = sc.nextLine();
			if (!desc.isEmpty()) { newBd.setDescription(desc); }
			affiche("Etat de la BD (Saisir \"Neuf\" ou \"Occasion\") : ");
			String etat = "";
			do {
				etat = sc.nextLine();
				if (!etat.equals("Neuf") && !etat.equals("Occasion")) {
					affiche("Saisie incorrecte. Réessayez : ");
				}
			} while (!etat.equals("Neuf") && !etat.equals("Occasion") && !etat.equals(""));
			if (!etat.equals("")) { newBd.setEtat(etat); }
			affiche("Prix : ");
			Float prix = 0.0f;
			do {
				prix = sc.nextFloat();
				if (prix < 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			} while (prix < 0);
			if (prix > 0) { newBd.setPrix(prix); }
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = "";
			do {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			} while (!dispo.equals("oui") && !dispo.equals("non") && !dispo.equals(""));
			if (!dispo.equals("")) { 
				if (dispo.equals("oui")) { newBd.setEnRayon(true); }
				if (dispo.equals("non")) { newBd.setEnRayon(false); } 
			}
			BandedessineeDAO bdDAO = new BandedessineeDAO();
			bdDAO.update(newBd);
			
			affiche("La BD a été mise à jour avec succès, redirection vers la fiche article");
			promptFicheBd(newBd);
			
		} else if (typeObj.equals("Figurine")) {
			affiche("\nRenseigner les informations de la figurine (Vous pouvez laisser les champs non modifiés vides) :\nDescription : ");
			Figurine newFig = (Figurine) obj;
			String desc = sc.nextLine();
			if (!desc.isEmpty()) { newFig.setDescription(desc); }
			affiche("Taille en cm (nombre entier) : ");
			Integer taille = 0;
			do {
				taille = sc.nextInt();
				if (taille < 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			} while (taille < 0);
			if (taille > 0) { newFig.setTaille(taille); }
			affiche("Prix : ");
			Float prix = 0.0f;
			do {
				prix = sc.nextFloat();
				if (prix < 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			} while (prix < 0);
			if (prix > 0) { newFig.setPrix(prix); }
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = "";
			do {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			} while (!dispo.equals("oui") && !dispo.equals("non") && !dispo.equals(""));
			if (!dispo.equals("")) { 
				if (dispo.equals("oui")) { newFig.setEnRayon(true); }
				if (dispo.equals("non")) { newFig.setEnRayon(false); } 
			}
			FigurineDAO figDAO = new FigurineDAO();
			figDAO.update(newFig);
			
			affiche("La BD a été mise à jour avec succès, redirection vers la fiche article");
			promptFicheFigurine(newFig);
		}
		
	}

	public static void promptConsultReservation(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		affiche("\nRéservations liées à l'Article réf. " + obj.getIdArticle());
		OptionDAO optDAO = new OptionDAO();
		List<Option> optionList = optDAO.findByArticleId(obj.getIdArticle());
		for (Option option : optionList) {
			System.out.println("* Réservation n°"+ option.getIdOption() + " de " + option.getUtilisateur().getUsername() + " - Date de réservation : " + option.getDateDebutOption() + " - Date limite : " + option.getDateFinOption());
		}
		
		affiche("\nAppuyer sur entrée pour revenir à la fiche Article");
		System.in.read();
		promptFicheArticle(obj);
	}

	public static void promptReservation(Article obj) throws InterruptedException, ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, IOException {
		if (obj.getEnRayon().equals(false)) { 
			affiche("\nL'article n'est pas disponible à la réservation. Redirection vers la fiche article...");
			Thread.sleep(3000);
			promptFicheArticle(obj);
		}
		else {
			affiche("\nVous êtes sur le point de réserver cet article, confirmez-vous l'opération ? (oui/non)");
			String confirm = "";
			while (!confirm.equals("oui") && !confirm.equals("non")) {
				confirm = sc.nextLine();
				if (!confirm.equals("oui") && !confirm.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			}
			if (confirm.equals("oui")) {
				OptionDAO optDAO = new OptionDAO();
				Date dateActuelle = new Date();
				Timestamp ts = new Timestamp(dateActuelle.getTime());
				Timestamp tsFin = new Timestamp(ts.getTime() + dureeResa);
				Option resa = new Option(ts, tsFin, obj, user);
				optDAO.create(resa);
				affiche("Votre article est réservé ! Vous pouvez le retirer en magasin dans un délai de 4 heures.");
				affiche("Retour sur la fiche Article...");
				Thread.sleep(3000);
				promptFicheArticle(obj);
			}
			if (confirm.equals("non")) {
				affiche("Redirection vers la fiche Article...");
				Thread.sleep(3000);
				promptFicheArticle(obj);
			}
		}
	}

	public static void promptFicheArticle(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		String typeObj = obj.getClass().getSimpleName();
		if (typeObj.equals("Bandedessinee")) { promptFicheBd((Bandedessinee) obj); }
		else if (typeObj.equals("Figurine")) { promptFicheFigurine((Figurine) obj); }
	}

	public static void promptAjouterArticle() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException, IOException {
		affiche("\nAjouter une BD ou une figurine ? (Saisir \"bd\" ou \"figurine\") : ");
		sc.nextLine();
		String objet = "";
		do {
			objet = sc.nextLine();
			if (!objet.equals("figurine") && !objet.equals("bd")) {
				affiche("Saisie incorrecte. Réessayez : ");
			}
		} while (!objet.equals("figurine") && !objet.equals("bd"));
		if (objet.equals("bd")) {
			affiche("\nRenseigner les informations de la BD :\nTitre : ");
			Bandedessinee newBd = new Bandedessinee();
			newBd.setLibelle(sc.nextLine());
			affiche("Collection : ");
			newBd.setCollection(sc.nextLine());
			affiche("Auteur : ");
			newBd.setAuteur(sc.nextLine());
			affiche("Editeur : ");
			newBd.setEditeur(sc.nextLine());
			affiche("Catégorie : ");
			newBd.setCategorie(sc.nextLine());
			affiche("Nbr. pages : ");
			Integer nbPages = 0;
			do {
				nbPages = sc.nextInt();
				if (nbPages <= 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			} while (nbPages <= 0);
			newBd.setNbrPages(nbPages);
			affiche("Description : ");
			sc.nextLine();
			newBd.setDescription(sc.nextLine());
			affiche("Etat de la BD (Saisir \"Neuf\" ou \"Occasion\") : ");
			String etat = "";
			do {
				etat = sc.nextLine();
				if (!etat.equals("Neuf") && !etat.equals("Occasion")) {
					affiche("Saisie incorrecte. Réessayez : ");
				}
			} while (!etat.equals("Neuf") && !etat.equals("Occasion"));
			newBd.setEtat(etat);
			affiche("Prix : ");
			Float prix = 0.0f;
			do {
				prix = sc.nextFloat();
				if (prix <= 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			} while (prix <= 0);
			newBd.setPrix(prix);
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = "";
			do {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			} while (!dispo.equals("oui") && !dispo.equals("non"));
			if (dispo.equals("oui")) { newBd.setEnRayon(true); }
			if (dispo.equals("non")) { newBd.setEnRayon(false); }
			
			BandedessineeDAO bdDAO = new BandedessineeDAO();
			bdDAO.create(newBd);
			
			affiche("La BD a été créée avec succès, redirection vers la fiche de la BD");
			ArticleDAO artDAO = new ArticleDAO();
			promptFicheBd(bdDAO.findByIdArticle(artDAO.findLastEntryId()));
		}
		else if (objet.equals("figurine")) {
			affiche("\nRenseigner les informations de la BD :\nDescription : ");
			Figurine newFig = new Figurine();
			newFig.setDescription(sc.nextLine());
			affiche("Taille de la figurine (en cm, nombre entier) : ");
			Integer taille = 0;
			do {
				taille = sc.nextInt();
				if (taille <= 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			} while (taille <= 0);
			newFig.setTaille(taille);
			affiche("Prix : ");
			sc.nextLine();
			Float prix = 0.0f;
			do {
				prix = sc.nextFloat();
				if (prix <= 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			} while (prix <= 0);
			newFig.setPrix(prix);
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = "";
			do {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			} while (!dispo.equals("oui") && !dispo.equals("non"));
			if (dispo.equals("oui")) { newFig.setEnRayon(true); }
			if (dispo.equals("non")) { newFig.setEnRayon(false); }
			
			FigurineDAO figDAO = new FigurineDAO();
			figDAO.create(newFig);
			
			affiche("La figurine a été créée avec succès, redirection vers la fiche de la figurine");
			ArticleDAO artDAO = new ArticleDAO();
			promptFicheFigurine(figDAO.findByIdArticle(artDAO.findLastEntryId()));
		}
		
	}
	
	public static void promptGererComptesUtilisateurs() {
		// TODO Auto-generated method stub
		
	}

	public static void promptMonCompte() {
		// TODO Auto-generated method stub
		
	}

	public static void promptOptionsList() {
		// TODO Auto-generated method stub
		
	}

	public static void promptCreerCompte() {
		// TODO Auto-generated method stub
		
	}

	public static void affiche(String message) {
		System.out.println(message);
	}
}