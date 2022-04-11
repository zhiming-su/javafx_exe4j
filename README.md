# javafx_exe4j
javafx_exe4j
--module-path "E:\Program Files\Java\javafx-sdk-17.0.1\lib" --add-modules javafx.controls,javafx.fxml

复制jdk的jmod下的java.desktop到javafx-jdk的lib目录下生成自己的jre
jlink --module-path "E:\Program Files\Java\javafx-sdk-17.0.1\lib" --add-modules javafx.controls,javafx.fxml,java.desktop --output myjre
