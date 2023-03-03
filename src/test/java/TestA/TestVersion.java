package TestA;

public class TestVersion {
    public static void main(String[] args) {
        String version = TestVersion.class.getPackage().getImplementationVersion();
        System.out.println(version);
    }
}
