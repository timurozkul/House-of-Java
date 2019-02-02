/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryv3.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class ConsoleIO {

    private Scanner sc = new Scanner(System.in);

    public int getInt(String prompt) {
        boolean test = true;
        int intNum = 0;
        do {
            try {
                System.out.println(prompt);
                intNum = Integer.parseInt(sc.nextLine());
                test = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
                sc = new Scanner(System.in);
                test = false;
            }
        } while (test == false);
        return intNum;
    }

    public int getIntInRange(String prompt, int minimum, int maximum) {
        boolean test = true;
        int intNum = 0;
        do {
            try {
                System.out.println(prompt);
                intNum = Integer.parseInt(sc.nextLine());
                while (intNum <= maximum || intNum >= minimum) {
                    System.out.println("Out Of Range! Try Again");
                    intNum = Integer.parseInt(sc.nextLine());
                }
                test = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
                sc = new Scanner(System.in);
                test = false;
            }
        } while (test == false);

        return intNum;

    }

    public String string(String prompt) {
        System.out.println(prompt);
        String s = sc.nextLine();
        return s;
    }

    public float getFloat(String prompt) {
        boolean test = true;
        float floatNum = 0f;
        do {
            try {
                System.out.println(prompt);
                floatNum = Float.parseFloat(sc.nextLine());
                test = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                sc = new Scanner(System.in);
                test = false;
            }
        } while (test == false);

        return floatNum;
    }

    public float getFloatInRange(String prompt, float fMin, float fMax) {
        boolean test = true;
        float floatNum = 0f;
        do {
            try {
                System.out.println(prompt);
                floatNum = Float.parseFloat(sc.nextLine());
                while (floatNum <= fMax || floatNum >= fMin) {
                    System.out.println("Out Of Range! Try Again");
                    floatNum = Float.parseFloat(sc.nextLine());
                }
                test = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                sc = new Scanner(System.in);
                test = false;
            }
        } while (test == false);

        return floatNum;

    }

    public double getDouble(String prompt) {
        boolean test = true;
        double doubleNum = 0;
        do {
            try {
                System.out.println(prompt);
                doubleNum = Double.parseDouble(sc.nextLine());
                test = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                sc = new Scanner(System.in);
                test = false;
            }
        } while (test == false);

        return doubleNum;
    }

    public double getDoubleInRange(String prompt, double dMin, double dMax) {
        boolean test = true;
        double doubleNum = 0;
        do {
            try {
                System.out.println(prompt);
                doubleNum = Double.parseDouble(sc.nextLine());
                while (doubleNum <= dMax || doubleNum >= dMin) {
                    System.out.println("Out Of Range! Try Again");
                    doubleNum = Float.parseFloat(sc.nextLine());
                }
                test = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                sc = new Scanner(System.in);
                test = false;
            }
        } while (test == false);

        return doubleNum;
    }

    public void printString(String promptMe) {
        System.out.println(promptMe);
    }
}
