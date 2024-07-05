package application;

import cli.CLI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CLI cli = new CLI(sc);

        System.out.println("Hello world!");
    }
}