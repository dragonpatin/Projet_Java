/*
//import java.util.*;
//import javax.swing.*;
//import java.awt.*;
//
//public class Interface{
//
//    //private entresortie donnee;
//
//    public Interface(){
//
//    }
//
//public void ChargerDonnee( String nomFichier , entresortie S){
//
//}
//
//public void SauvegarderDonnee ( String nomFichier , entresortie S){
//
//}
//
//public void AfficheObjet(Vector objet){
//
//}
//
//public void AffichePiece(Vector piece){
//
//}
//
//public void AfficheInfosObjet(Objet o){
//	System.out.println("Le nom de l'objet est" + o.NomObjet);
//	System.out.println("L'objet est dans la" + o.PieceMaison);
//	if(o.Switch)System.out.println("L'objet est allumé");
//	else System.out.println("L'objet est éteint");
//	System.out.println("La duree d'utilisation est de" + o.Duree_utilisation);
//	System.out.println("Son adresse Mac est" + o.AdresseMAC);
//}
//
//public void AfficheConsommationObjet(Objet o){
//	System.out.println("La consommation de cette objet est" + o.Consommation);
//}
//
////public void AfficheConsommationGlobale ( Vector objet ){
////	int consoG=0;
////	for(int i=0;i<objet.size();i++){
////		consoG+= objet.Consommation[i];
////	}
////	System.out.println("La consommation globale est" + consoG);
////}
//
//public void AjoutObjet(entresortie donnee , Objet o){
//
//}
//
//public void AjoutObjetFavoris ( entresortie donnee , Objet o){
//
//}
//
//public void AjoutPiece ( entresortie donnee , Piece p){
//
//}
//
//public void AjoutObjetDansPiece (){
//
//}
//
//
//public void echanger(int a, int b){
//	int temp;
//	temp=a;
//	a=b;
//	b=temp;
//}
//
//public int Partitionner_Objet(Vector O, int premier , int dernier , int pivot){
//	echanger(O[pivot],O[dernier]);
//	int j=premier;
//    for(int i=premier;i<dernier-1;i++)
//         {
//          if (O[i]<O[dernier])
//              {
//              echanger(O[i],O[j]);
//              j++;
//              }
//          }
//     echanger(O[dernier],O[j]);
//     return j;
//}
//
//public void tri_rapide_Objet(Vector O, int premier , int dernier){
//	if(premier < dernier){
//		int pivot= O[premier];
//		pivot= Partitionner_Objet(O,premier,dernier,pivot);
//		tri_rapide_Objet(O,premier,pivot-1);
//		tri_rapide_Objet(O,pivot+1,dernier);
//
//	}
//}
//
//public int Partitionner_Piece(Vector O, int premier , int dernier , int pivot){
//	echanger(O[pivot],O[dernier]);
//	int j=premier;
//    for(int i=premier;i<dernier-1;i++)
//         {
//          if (O[i]<O[dernier])
//              {
//              echanger(O[i],O[j]);
//              j++;
//              }
//          }
//     echanger(O[dernier],O[j]);
//     return j;
//}
//
//public void tri_rapide_Piece(Vector O, int premier , int dernier){
//	if(premier < dernier){
//		int pivot= O[premier];
//		pivot= Partitionner_Objet(O,premier,dernier,pivot);
//		tri_rapide_Piece(O,premier,pivot-1);
//		tri_rapide_Piece(O,pivot+1,dernier);
//	}
//}
//
//
//
//public boolean test_nom_Objet ( String nomObjet, Vector O, int n){
//	for(int i=0;i<n-1;i++){
//		if(nomObjet == O[i]) return true;
//	}
//	return false;
//}
//
//public boolean test_nom_Piece( String nomPiece, Vector O, int n){
//		for(int i=0;i<n-1;i++){
//		if(nomPiece == O[i]) return true;
//	}
//	return false;
//}
//
//
//public void AppelAutomatiqueRecuperationConsommation( entresortie donnee, int temps){
//
//}
//
//public void lancerSimulateur(Simulateur sim){
//
//}
//}
*/
