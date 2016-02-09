package fr.HebeDede.ui;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.HebeDede.model.Article;
import fr.HebeDede.model.Bandedessinee;
import fr.HebeDede.model.Figurine;
import fr.HebeDede.model.Option;
import fr.HebeDede.model.Utilisateur;
import fr.HebeDede.repositories.BandedessineeDAO;
import fr.HebeDede.repositories.FigurineDAO;
import fr.HebeDede.repositories.OptionDAO;
import fr.HebeDede.service.ConsoleService;

public class ConsoleOption {
	
	static OptionDAO optionDAO = new OptionDAO();
	
	public static void promptOptionsListClient(Utilisateur user) {
		ConsoleService.affiche ("\n R�servations li�es � l'utilisateur " + user.getUsername() + " :");
		List<Option> optionList = optionDAO.findByUtilisateur(user);
		List<Integer> optionId = new ArrayList<Integer>();
		List<String> menuList = new ArrayList<String>();
		for (Option option : optionList) {
			BandedessineeDAO bdDAO = new BandedessineeDAO();
			FigurineDAO figDAO = new FigurineDAO();
			Bandedessinee bd = bdDAO.findByIdArticle(option.getArticle().getIdArticle());
			Figurine fig = figDAO.findByIdArticle(option.getArticle().getIdArticle());
			if (bd != null) {
				ConsoleService.affiche("\nR�servation n�" + option.getIdOption() + " - Article n�: " + option.getArticle().getIdArticle() + 
						"\nLibell� : " + bd.getLibelle() + " - Collection : " + bd.getCollection() + 
						"\nArticle disponible jusqu'au " + option.getDateFinOption().getTime());
			}
			else if (fig != null) {
				ConsoleService.affiche("\nR�servation n�" + option.getIdOption() + " - Article n�: " + option.getArticle().getIdArticle() + 
						"\nLibell� : " + fig.getDescription() + 
						"\nArticle disponible jusqu'au " + option.getDateFinOption().getTime());
			}
			menuList.add(option.getArticle().getIdArticle() + ". Annuler la r�servation n�" + option.getIdOption());
			optionId.add(option.getIdOption());
		}
		ConsoleService.affiche("Menu :");
		for (String string : menuList) {
			ConsoleService.affiche(string);
		}
		ConsoleService.affiche("\n0. Retour au menu principal");
		
		Integer choice = ConsoleService.choixMenuIntList(optionId);
		
	    if (choice != 0) {
	    	Option option = optionDAO.find(choice);
	    	promptAnnulerOption(option, user);
	    }
	    else if (choice == 0) {
	    	Console.promptMenu();
	    }
		
	}

	public static void promptOptionsListAll() {
		ConsoleService.affiche("\n******* Liste des R�servation *******\n");
		List<Option> optionList = optionDAO.findAll();
		List<Integer> optionId = new ArrayList<Integer>();
		for (Option option : optionList) {
			BandedessineeDAO bdDAO = new BandedessineeDAO();
			FigurineDAO figDAO = new FigurineDAO();
			Bandedessinee bd = bdDAO.findByIdArticle(option.getArticle().getIdArticle());
			Figurine fig = figDAO.findByIdArticle(option.getArticle().getIdArticle());
			if (bd != null) {
				ConsoleService.affiche("\nR�servation n�" + option.getIdOption() + " - Article n�: " + option.getArticle().getIdArticle() + 
						"\nLibell� : " + bd.getLibelle() + " - Collection : " + bd.getCollection() + 
						"\nArticle disponible jusqu'au " + option.getDateFinOption().getTime());
			}
			else if (fig != null) {
				ConsoleService.affiche("\nR�servation n�" + option.getIdOption() + " - Article n�: " + option.getArticle().getIdArticle() + 
						"\nLibell� : " + fig.getDescription() + 
						"\nArticle disponible jusqu'au " + option.getDateFinOption().getTime());
			}
			optionId.add(option.getIdOption());
		}
		ConsoleService.affiche("Menu :");
		ConsoleService.affiche("1. Annuler une r�servation");
		ConsoleService.affiche("\n0. Retour au menu principal");
		
		Integer choice = ConsoleService.choixMenuMinMax(0,optionId.get(optionId.size() -1).intValue());
	
	    if (choice == 1) {
	    	ConsoleService.affiche("\nRenseigner le numero de l'option � annuler : ");
	    	Integer idOption = 0;
	    	do {
	    		idOption = Console.sc.nextInt();
	    		if (!optionId.contains(idOption)) {
	    			ConsoleService.affiche("Le num�ro d'option renseign� est incorrect.");
	    		}
	    	} while (!optionId.contains(idOption));
	    	Option option = optionDAO.find(idOption);
	    	Utilisateur optionUser = option.getUtilisateur();
	    	promptAnnulerOption(option, optionUser);
	    }
	    else if (choice == 0) {
	    	Console.promptMenu();
	    }
	}

	public static void promptAnnulerOption(Option option, Utilisateur user) {
		List<Option> optionList = optionDAO.findByUtilisateur(user);
		if (!optionList.contains(option)) {
			ConsoleService.affiche("Cette option n'appartient pas � l'utilisateur " + user.getUsername() + ", annulation impossible.");
		}
		else {		
			ConsoleService.affiche("\nVous �tes sur le point d'annuler l'option n�"+ option.getIdOption() + ".\n�tes-vous s�r ? (oui/non) ");
			String choix = "";
			do {
				Console.sc.nextLine();
				choix = Console.sc.nextLine();
				if (!choix.equals("oui") && !choix.equals("non")) {
					ConsoleService.affiche("\nChoix incorrect.");
				}
			} while (choix.equals("") && !choix.equals("oui") && !choix.equals("non"));
			
			if(choix.equals("oui")) {
				
				optionDAO.delete(option);
				ConsoleService.affiche("R�servation annul�e !");
			}
		}
		
		ConsoleService.affiche("Retour au menu principal...");
		ConsoleService.sleep(3000);
		Console.promptMenu();
		
	}

	public static void promptConsultReservation(Article obj) {
		ConsoleService.affiche("\nR�servations li�es � l'Article r�f. " + obj.getIdArticle());
		List<Option> optionList = optionDAO.findByArticle(obj);
		for (Option option : optionList) {
			System.out.println("* R�servation n�"+ option.getIdOption() + " de " + option.getUtilisateur().getUsername() + " - Date de r�servation : " + option.getDateDebutOption() + " - Date limite : " + option.getDateFinOption());
		}
		
		ConsoleService.affiche("\nAppuyer sur entr�e pour revenir � la fiche Article");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ConsoleArticle.findFicheArticle(obj);
	}

	public static void promptReservation(Article obj) {
		if (obj.getEnRayon().equals(false)) { 
			ConsoleService.affiche("\nL'article n'est pas disponible � la r�servation. Redirection vers la fiche article...");
			ConsoleService.sleep(3000);
			ConsoleArticle.findFicheArticle(obj);
		}
		else {
			ConsoleService.affiche("\nVous �tes sur le point de r�server cet article, confirmez-vous l'op�ration ? (oui/non)");
			String confirm = "";
			while (!confirm.equals("oui") && !confirm.equals("non")) {
				confirm = Console.sc.nextLine();
				if (!confirm.equals("oui") && !confirm.equals("non")) {
					ConsoleService.affiche("Merci de saisir \"oui\" ou \"non\") : . R�essayez : ");
				}
			}
			if (confirm.equals("oui")) {
				Date dateActuelle = new Date();
				Timestamp ts = new Timestamp(dateActuelle.getTime());
				Timestamp tsFin = new Timestamp(ts.getTime() + Console.dureeResa);
				Option resa = new Option(ts, tsFin, obj, Console.user);
				optionDAO.create(resa);
				obj.setEnRayon(false);
				ConsoleArticle.articleDAO.update(obj);
				ConsoleService.affiche("Votre article est r�serv� ! Vous pouvez le retirer en magasin dans un d�lai de 4 heures.");
				ConsoleService.affiche("Retour sur la fiche Article...");
				ConsoleService.sleep(3000);
				ConsoleArticle.findFicheArticle(obj);
			}
			if (confirm.equals("non")) {
				ConsoleService.affiche("Redirection vers la fiche Article...");
				ConsoleService.sleep(3000);
				ConsoleArticle.findFicheArticle(obj);
			}
		}
	}

}
