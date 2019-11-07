package asd;

import javazoom.jl.player.Player;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

public class Main {
    static ChromeOptions options = new ChromeOptions();
    static ChromeDriver driver = new ChromeDriver(options);
    public static void main(String[] args) throws InterruptedException {

        try {
            InputStream in2 = new FileInputStream("Begin.mp3");
            Player playMP3 = new Player(in2);
            playMP3.play();


        } catch (Exception e) {

        }

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        } else if (os.contains("mac"))
            System.setProperty("webdriver.chrome.driver", "chromedriver");

        driver.get("https://pastel.diplomatie.gouv.fr/rdvinternet/html-4.02.00/frameset/frameset.html?lcid=1&sgid=102&suid=1"); //launch Firefox and open Url
//        Thread.sleep(2000);
        driver.switchTo().frame(1);
        Thread.sleep(5000);

        driver.switchTo().frame(0);
        Thread.sleep(5000);

        tryClick("#item2_0_0");


        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        Thread.sleep(2000);

        tryClick("#ccg");
//        driver.findElement(By.cssSelector("#Position_ccg")).click();
//        Thread.sleep(2000);

        tryClick("#boutonSuivant_link");
        boolean success = false;
        int errorTimes = 0;
        while(!success){

            try{

                if (driver.switchTo().alert().getText().equals("We are currently at full capacity. Please try again later.")) {
                    driver.switchTo().alert().accept();
                    driver.findElement(By.cssSelector("#boutonPrecedent_link")).click();
                    Thread.sleep(2000);
                    if(!driver.findElement(By.cssSelector("#ccg")).getAttribute("selected").equals("true"))
                        driver.findElement(By.cssSelector("#ccg")).click();
                    driver.findElement(By.cssSelector("#boutonSuivant_link")).click();
                    Thread.sleep(2000);
                }
                else {
                    success = true;
                    break;
                }
            }catch (Exception e){
                Thread.sleep(1000);
                errorTimes++;
                if(errorTimes == 5){
                    System.out.println("5 errors hit");
                    driver.findElement(By.cssSelector("#boutonSuivant_link")).click();
                    driver.findElement(By.cssSelector("#boutonPrecedent_link")).click();
                    errorTimes = 0;
                }
            }
        }
        try {
            System.out.println("FOUND");
            InputStream in;
            Player playMP3;
            for (int i = 0; i < 100; i++) {
                in = new FileInputStream("Appointment detected.mp3");
                playMP3 = new Player(in);
                playMP3.play();
            }

        } catch (Exception e) {

        }


    }
    public static void tryClick(String IDCss){
        boolean success = false;
        while(!success){
            try {
//                driver.findElement(By.cssSelector(IDCss)).getAttribute("selected")
                Thread.sleep(1009);
                driver.findElement(By.cssSelector(IDCss)).click();
                success = true;
                Thread.sleep(1009);
            }catch (Exception e){

            }
        }
    }
}
