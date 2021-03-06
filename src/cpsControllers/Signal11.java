/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsControllers;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author koperingnet
 */
public class Signal11 {
    
    HelperController helper;
    HelperController.rodzaj_sygnalu _rodzaj = HelperController.rodzaj_sygnalu.DYSKRETNY;

    public Signal11(HelperController helper) {
        this.helper = helper;
    }
    
    private static final Random random = new Random();
    
    /**
     * Szum impulsowy
     * 
     * @param t
     * @return 
     */
    public double sygnalS11(double t) {
        if (helper.skok == 0) {
            return 0;
        } else if (random.nextInt(100) < helper.skok) {
            return helper.getA();
        } else {
            return 0;
        }
    }
    
     /**
     * Obliczanie wartości średniej
     *
     * @param punktyY
     * @return
     */
    public double obl_sredniawartosc(ArrayList<Double> punktyY) {
        int i;
        double srednia = 0;
        int size = 0;
        if (_rodzaj == HelperController.rodzaj_sygnalu.DYSKRETNY) {
            if (helper.gettyp() == 3 || helper.gettyp() == 4 || helper.gettyp() == 5
                    || helper.gettyp() == 6 || helper.gettyp() == 7
                    || helper.gettyp() == 8) {
                double _krok = helper.gett1();
                int liczba = 0;
                while (_krok < helper.getT()) {
                    srednia = srednia + helper.getPunktzindexu(liczba, punktyY);
                    liczba = liczba + 1;
                    _krok = _krok + helper.getkroczek();
                }
                size = liczba;
            } else {
                for (i = 0; i < punktyY.size(); i++) {
                    srednia = srednia + helper.getPunktzindexu(i, punktyY);
                }
                size = punktyY.size();
            }
        }
        return srednia / size;
    }


    /**
     * Obliczanie średniej wartości bezwzględnej
     * 
     * @param punktyY
     * @return 
     */
    public double obl_sredniawartoscbezwzgledna(ArrayList<Double> punktyY) {
        int i;
        double srednia = 0;
        double liczba = 0;
        if (_rodzaj == HelperController.rodzaj_sygnalu.DYSKRETNY) {
            for (i = 0; i < punktyY.size(); i++) {
                liczba = Math.abs(helper.getPunktzindexu(i, punktyY));
                srednia = srednia + liczba;
            }
        }
        return srednia / punktyY.size();
    }

    public double obl_mocsrednia(ArrayList<Double> punktyY) {
        int i;
        double moc = 0;
        if (_rodzaj == HelperController.rodzaj_sygnalu.DYSKRETNY) {
            for (i = 0; i < punktyY.size(); i++) {
                moc = moc + (helper.getPunktzindexu(i, punktyY) * helper.getPunktzindexu(i, punktyY));
            }
        }
        return moc / punktyY.size();
    }

    /**
     * Obliczanie wartości skutecznej
     * 
     * @param punktyY
     * @return 
     */
    public double obl_wartoscskuteczna(ArrayList<Double> punktyY) {
        int i;
        double moc = 0;
        if (_rodzaj == HelperController.rodzaj_sygnalu.DYSKRETNY) {
            for (i = 0; i < punktyY.size(); i++) {
                moc = moc + (helper.getPunktzindexu(i, punktyY) * helper.getPunktzindexu(i, punktyY));
            }
        }
        return Math.sqrt(moc / punktyY.size());
    }

    /**
     * Obliczanie wariacji sygnału
     * 
     * @param punktyY
     * @return 
     */
    public double obl_wariancja(ArrayList<Double> punktyY) {
        int i;
        double wariancja = 0;
        if (_rodzaj == HelperController.rodzaj_sygnalu.DYSKRETNY) {
            for (i = 0; i < punktyY.size(); i++) {
                double _wartosc = helper.getPunktzindexu(i, punktyY)
                        - this.obl_sredniawartosc(punktyY);
                wariancja = wariancja + (_wartosc * _wartosc);
            }
        }
        return wariancja / punktyY.size();
    }
}
