package fr.HebeDede.ui;

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
	
	public static void promptLogin() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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

	public static void promptMenu() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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
		
	public static void chooseMenu() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException { 
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
	
	public static void selectMenu(int choice) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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
	
	public static void promptArticleList() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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
	
	public static void chooseArticleList() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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
	
	public static void selectArticleList(int choice) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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
			}
		}
	}

	public static void ouvrirFicheArticle() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		affiche("Renseigner la référence de l'article à consulter :");
		Integer idArticle = 0;
		ArticleDAO artDAO = new ArticleDAO();
		FigurineDAO figDAO = new FigurineDAO();
		BandedessineeDAO bdDAO = new BandedessineeDAO();
		while (!idArticle.equals(artDAO.find(idArticle).getIdArticle())) {
			idArticle = sc.nextInt();
			if (!idArticle.equals(artDAO.find(idArticle).getIdArticle())) {
				affiche("La référence article est inexistante, veuillez réessayer.");
			}
		}
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
		else {
			return;
		}
	}

	public static void promptFicheBd(Bandedessinee bd) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		affiche("\n******* Fiche Bande déssinée *******\nRéf. article " + bd.getIdArticle());
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
		if (user.getRole().equals("Client")) {
			affiche("1. Réserver la BD");
			affiche("0. Retour à la liste des Articles");
		}
		else if (user.getRole().equals("Employe") || user.getRole().equals("Chef")) {
			affiche("1. Consulter les réservations de la BD");
			affiche("2. Modifier les informations de la BD");
			affiche("3. Supprimer la figurine du catalogue");
			affiche("0. Retour à la liste des Articles");
		}
		else {
			affiche("0. Retour à la liste des Articles");
		}
		chooseFicheBd(bd);
	}

	public static void promptFicheFigurine(Figurine fig) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		affiche("\n******* Fiche Figurine *******\nRéf. article " + fig.getIdArticle());
		affiche("Description : " + fig.getDescription());
		affiche("Taille figurine : " + fig.getTaille());
		affiche("\nPrix : " + fig.getPrix() + "€");
		affiche("Disponible en magasin : " + fig.dispo());
		
		affiche("\nMenu :");
		if (user.getRole().equals("Client")) {
			affiche("1. Réserver la figurine");
			affiche("0. Retour à la liste des Articles");
		}
		else if (user.getRole().equals("Employe") || user.getRole().equals("Chef")) {
			affiche("1. Consulter les réservations de la figurine");
			affiche("2. Modifier les informations de la figurine");
			affiche("3. Supprimer la figurine du catalogue");
			affiche("0. Retour à la liste des Articles");
		}
		else {
			affiche("0. Retour à la liste des Articles");
		}
		
		chooseFicheFigurine(fig);
	}

	public static void chooseFicheBd(Bandedessinee bd) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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

	public static void chooseFicheFigurine(Figurine fig) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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

	public static void selectFicheBd(int choice, Bandedessinee bd) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		if (user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					promptReservation(bd);
					break;
				case 0:
					promptArticleList();
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
					String ok = null;
					while (!ok.equals("oui") && !ok.equals("non")) {
						ok = sc.nextLine();
						if (!ok.equals("oui") && !ok.equals("non")) {
							affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
						}
					}
					if (ok.equals("oui")) { supprimerFiche(bd); }
					if (ok.equals("non")) { promptFicheBd(bd); }
					break;
				case 0:
					promptArticleList();
					break;
			}
		}
		else {
			affiche("0. Retour à la liste des Articles");
		}
	}

	public static void selectFicheFigurine(int choice, Figurine fig) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		if (user.getRole().equals("Client")) {
			switch (choice) {
				case 1:
					promptReservation(fig);
					break;
				case 0:
					promptArticleList();
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
					String ok = null;
					while (!ok.equals("oui") && !ok.equals("non")) {
						ok = sc.nextLine();
						if (!ok.equals("oui") && !ok.equals("non")) {
							affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
						}
					}
					if (ok.equals("oui")) { supprimerFiche(fig); }
					if (ok.equals("non")) { promptFicheFigurine(fig); }
					break;
				case 0:
					promptArticleList();
					break;
			}
		}
		else {
			affiche("0. Retour à la liste des Articles");
		}
	}

	public static void supprimerFiche(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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

	public static void modifFiche(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
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
			Integer nbPages = null;
			while (nbPages <= 0 && !nbPages.equals(null)) {
				nbPages = sc.nextInt();
				if (nbPages <= 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			}
			if (!nbPages.equals(null)) { newBd.setNbrPages(nbPages); }
			affiche("Description : ");
			sc.nextLine();
			String desc = sc.nextLine();
			if (!desc.isEmpty()) { newBd.setDescription(desc); }
			affiche("Etat de la BD (Saisir \"Neuf\" ou \"Occasion\") : ");
			String etat = null;
			while (!etat.equals("Neuf") && !etat.equals("Occasion") && !etat.equals(null)) {
				etat = sc.nextLine();
				if (!etat.equals("Neuf") && !etat.equals("Occasion")) {
					affiche("Saisie incorrecte. Réessayez : ");
				}
			}
			if (etat.isEmpty()) { newBd.setEtat(etat); }
			affiche("Prix : ");
			Float prix = null;
			while (prix <= 0 && !prix.equals(null)) {
				prix = sc.nextFloat();
				if (prix <= 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			}
			if (!prix.equals(null)) { newBd.setPrix(prix); }
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = null;
			while (!dispo.equals("oui") && !dispo.equals("non") && !dispo.equals(null)) {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			}
			if (!dispo.equals(null)) { 
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
			Integer taille = null;
			while (taille <= 0 && !taille.equals(null)) {
				taille = sc.nextInt();
				if (taille <= 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			}
			if (!taille.equals(null)) { newFig.setTaille(taille); }
			affiche("Prix : ");
			Float prix = null;
			while (prix <= 0 && !prix.equals(null)) {
				prix = sc.nextFloat();
				if (prix <= 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			}
			if (!prix.equals(null)) { newFig.setPrix(prix); }
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = null;
			while (!dispo.equals("oui") && !dispo.equals("non") && !dispo.equals(null)) {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			}
			if (!dispo.equals(null)) { 
				if (dispo.equals("oui")) { newFig.setEnRayon(true); }
				if (dispo.equals("non")) { newFig.setEnRayon(false); } 
			}
			FigurineDAO figDAO = new FigurineDAO();
			figDAO.update(newFig);
			
			affiche("La BD a été mise à jour avec succès, redirection vers la fiche article");
			promptFicheFigurine(newFig);
		}
		
	}

	public static void promptConsultReservation(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		affiche("\nRéservations liées à l'Article réf. " + obj.getIdArticle());
		OptionDAO optDAO = new OptionDAO();
		List<Option> optionList = optDAO.findByArticleId(obj.getIdArticle());
		for (Option option : optionList) {
			System.out.println("* Réservation n°"+ option.getIdOption() + "de " + option.getUtilisateur().getUsername() + " - Date de réservation : " + option.getDateDebutOption() + " - Date limite : " + option.getDateFinOption());
		}
		
		affiche("\nAppuyer sur entrée pour revenir à la fiche Article");
		promptFicheArticle(obj);
	}

	public static void promptReservation(Article obj) throws InterruptedException, ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException {
		if (obj.getEnRayon().equals(false)) { 
			affiche("\nL'article n'est pas disponible à la réservation. Redirection vers la fiche article...");
			Thread.sleep(3000);
			promptFicheArticle(obj);
		}
		else {
			affiche("\nVous êtes sur le point de réserver cet article, confirmez-vous l'opération ? (oui/non)");
			String confirm = null;
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

	public static void promptFicheArticle(Article obj) throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		String typeObj = obj.getClass().getSimpleName();
		if (typeObj.equals("Bandedessinee")) { promptFicheBd((Bandedessinee) obj); }
		else if (typeObj.equals("Figurine")) { promptFicheFigurine((Figurine) obj); }
	}

	public static void promptAjouterArticle() throws ClassNotFoundException, IllegalAccessException, UtilisateurInconnuException, InterruptedException {
		affiche("\nAjouter une BD ou une figurine ? (Saisir \"bd\" ou \"figurine\") : ");
		sc.nextLine();
		String objet = null;
		while (!objet.equals("figurine") && !objet.equals("bd")) {
			objet = sc.nextLine();
			if (!objet.equals("figurine") && !objet.equals("bd")) {
				affiche("Saisie incorrecte. Réessayez : ");
			}
		}
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
			Integer nbPages = null;
			while (nbPages <= 0) {
				nbPages = sc.nextInt();
				if (nbPages <= 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			}
			newBd.setNbrPages(nbPages);
			affiche("Description : ");
			sc.nextLine();
			newBd.setDescription(sc.nextLine());
			affiche("Etat de la BD (Saisir \"Neuf\" ou \"Occasion\") : ");
			String etat = null;
			while (!etat.equals("Neuf") && !etat.equals("Occasion")) {
				etat = sc.nextLine();
				if (!etat.equals("Neuf") && !etat.equals("Occasion")) {
					affiche("Saisie incorrecte. Réessayez : ");
				}
			}
			newBd.setEtat(etat);
			affiche("Prix : ");
			Float prix = null;
			while (prix <= 0) {
				prix = sc.nextFloat();
				if (prix <= 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			}
			newBd.setPrix(prix);
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = null;
			while (!dispo.equals("oui") && !dispo.equals("non")) {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			}
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
			Integer taille = null;
			while (taille <= 0) {
				taille = sc.nextInt();
				if (taille <= 0) {
					affiche("Renseignez un nombre > 0. Réessayez : ");
				}
			}
			newFig.setTaille(taille);
			affiche("Prix : ");
			sc.nextLine();
			Float prix = null;
			while (prix <= 0) {
				prix = sc.nextFloat();
				if (prix <= 0) {
					affiche("Renseignez un prix > 0. Réessayez : ");
				}
			}
			newFig.setPrix(prix);
			affiche("Disponiblité en magasin (Saisir \"oui\" ou \"non\") : ");
			sc.nextLine();
			String dispo = null;
			while (!dispo.equals("oui") && !dispo.equals("non")) {
				dispo = sc.nextLine();
				if (!dispo.equals("oui") && !dispo.equals("non")) {
					affiche("Merci de saisir \"oui\" ou \"non\") : . Réessayez : ");
				}
			}
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