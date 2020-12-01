import exception.ParameterOutOfBoundException;
import service.Calculator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParameterOutOfBoundException {
        boolean running = true;
        showASCIIArt();
        Scanner command = new Scanner(System.in);
        do {
            System.out.println("Start a new calculation [y/n]: ");
            String text = command.nextLine();
            Calculator calculator = new Calculator();
            switch(text){
                case "y":
                    try {
                        System.out.println("Enter specific gravity value: ");
                        text = command.nextLine();
                        BigDecimal gravity = BigDecimal.valueOf(Double.valueOf(text));
                        System.out.println("Enter observed temperature value: ");
                        text = command.nextLine();
                        BigDecimal temperature = BigDecimal.valueOf(Double.valueOf(text));
                        System.out.println("Calculation started!");
                        calculator.calculateCTLCorrection(gravity, temperature);
                    } catch (Exception ex) {
                        System.out.println(ex);
                        System.out.println("Error! please enter proper values");
                    }
                    break;
                case "exit":
                case "n":
                    System.out.println("Application Closed");
                    running = false;
                    break;
                default:
                    System.out.println("Command not recognized!");
                    break;
            }
        } while(running);
        command.close();
    }

    public static void showASCIIArt() {
        int width = 100;
        int height = 30;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 20));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("NGL CTL", 10, 20);


        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(sb);
        }

    }
}
