package java9.resources.test1;

//JDK 7中
//public class Tester {
//    public static void main(String[] args) throws IOException {
//        System.out.println(readData("test"));
//    }
//    static String readData(String message) throws IOException {
//        Reader inputString = new StringReader(message);
//        BufferedReader br = new BufferedReader(inputString);
//        try (BufferedReader br1 = br) {
//            return br1.readLine();
//        }
//    }
//}


//public class Tester {
//    public static void main(String[] args) throws IOException {
//        System.out.println(readData("test"));
//    }
//    static String readData(String message) throws IOException {
//        Reader inputString = new StringReader(message);
//        BufferedReader br = new BufferedReader(inputString);
//        try (br) {
//            return br.readLine();
//        }
//    }
//}