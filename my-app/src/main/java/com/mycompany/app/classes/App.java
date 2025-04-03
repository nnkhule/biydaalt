package com.mycompany.app.classes;


public class App {
    public static void main(String[] args) throws Exception {
        Commander commander = new Commander();
        commander.sayHello();
        commander.startApp();
    }
}

//mvn exec:java -Dexec.mainClass="com.mycompany.app.classes.App"
//
//mvn exec:java -Dexec.mainClass="com.mycompany.app.classes.App" -Dexec.args="c:\Mathematic\Gamshig\buteelt\biydaalt\my-app\db.txt"