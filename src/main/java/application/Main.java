
package application;



public class Main {
    public static void main(String[] args) {
        
        Property propertyToUpdate = new Property();
        Property propertyToArchive = new Property();

       
        PropertyUpdater.updateProperty(propertyToUpdate);
        PropertyUpdater.archiveProperty(propertyToArchive);

        // Other functionalities...
    }
}
//sss