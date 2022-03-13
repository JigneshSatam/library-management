if [ -z "$PATH_TO_JAVAFX_LIB" ]
then
      echo "PATH_TO_JAVAFX_LIB is empty"
      echo "export PATH_TO_JAVAFX_LIB=<your_javafx_lib_path>"
      exit
fi

javac -sourcepath src/ -d bin/ --module-path lib --add-modules javafx.controls,javafx.fxml src/library/Main.java

java --module-path $PATH_TO_JAVAFX_LIB --add-modules javafx.controls,javafx.fxml --enable-preview -cp "bin:lib/*" library.Main

rm -r bin/library
