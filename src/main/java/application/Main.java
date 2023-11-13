package application;

public class Main {

  public static void main(String[] args) {
    
        // create style object
        Style style = new Style();
        style.setStyleId(1);
        style.setPStyle("Colonial");

        // create agent object
        Agent agent = new Agent(); 
        agent.setAgentId(1);
        agent.setName("John Smith");
        agent.setPhone("1234567890");
        agent.setFax("0987654321");
        agent.setEmail("amnah@email.com");
        agent.setUsername("jsmith");
        agent.setPassword("password1");

        // create garage type object
        GarageType garageType = new GarageType();
        garageType.setGarageId(1);
        garageType.setGType("Attached");

        // create propertyType object
        PropertyType propertyType = new PropertyType();
        propertyType.setTypeId(1);
        propertyType.setPType("Residential");

        // create property object
        Property property = new Property();
        property.setId(1);
        property.setStreet("123 Main St");
        property.setCity("Limerick");
        property.setListingNum(123456);
        property.setStyleId(1);
        property.setTypeId(1);
        property.setBedrooms(3);
        property.setBathrooms(2.5f);
        property.setSquarefeet(2000);
        property.setBerRating("B3");
        property.setDescription("This beautiful colonial style house is located on a quiet street.");
        property.setLotsize("0.5 acres");
        property.setGaragesize((byte) 2);
        property.setGarageId(1);
        property.setAgentId(1);
        property.setPhoto("propertyphoto.jpg");
        property.setPrice(350000);
        property.setDateAdded(LocalDate.of(2023, 2, 2));
        property.setStyle(style);
        property.setPropertyType(propertyType);
        property.setGarageType(garageType);
        property.setAgent(agent);

        
        System.out.println("\nEnter the number of the field to update:");
        System.out.println("1. Street Address");
        System.out.println("2. City Location");
        System.out.println("3. Listing Number");
        System.out.println("4. Style ID");
        System.out.println("5. Property Type ID");
        System.out.println("6. Bedrooms");
        System.out.println("7. Bathrooms");
        System.out.println("8. Square Feet");
        System.out.println("9. BER Rating");
        System.out.println("10. Description");
        System.out.println("11. Lot Size");
        System.out.println("12. Garage Size");
        System.out.println("13. Garage Type ID");
        System.out.println("14. Agent ID");
        System.out.println("15. Property Photo");
        System.out.println("16. Price");
        System.out.println("17. Date Added");



  }
}
//mmnn

   
               
