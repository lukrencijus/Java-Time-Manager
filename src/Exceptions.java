import javax.swing.*;

public class Exceptions extends Exception{

    public Exceptions(String errorMessage, Throwable err){
            super(errorMessage, err);
        }

    public static class EmptyFieldException extends Exceptions{
        public EmptyFieldException(){
            super("This field can't be empty", null);
            JOptionPane.showMessageDialog(tryGUI.frame, "This field can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static class IllegalFormat extends Exceptions{
        public IllegalFormat(){
            super("Illegal format", null);
            JOptionPane.showMessageDialog(tryGUI.frame, "Illegal format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static class NotFound extends Exceptions{
        public NotFound(){
            super("Not Found", null);
            JOptionPane.showMessageDialog(tryGUI.frame, "No matching activities were found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
