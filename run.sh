javac -sourcepath src/ -d bin/ --module-path lib --add-modules javafx.controls,javafx.fxml src/library/Main.java

java --module-path /Users/jigneshsatam/JavaFX/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml --enable-preview -cp "bin:lib/*" library.Main

rm -r bin/library
