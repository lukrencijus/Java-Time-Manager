public class Exceptions extends Exception{

    public Exceptions(String errorMessage, Throwable err){
            super(errorMessage, err);
        }
        
    public static class EmptyFieldException extends Exceptions{
        public EmptyFieldException(){
            super("This field can't be empty", null);
        }
    }
    public static class IllegalFormat extends Exceptions{
        public IllegalFormat(){
            super("Illegal format", null);
        }
    }


    
}
