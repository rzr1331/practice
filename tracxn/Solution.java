import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Solution {

    /*
     *  ALL TEST CASES PASSED
     */


/*
=================
Test Case 1 :
=================
Input :
5
3
FATOil FIBERSpinach CARBRice FATCheese FIBERBeans
=================
Output :
---FATOil:FIBERSpinach:FATCheese-
=================
*/

/*
=================
Test Case 2   :
=================
Input :
6
3
FATOil FATCheese FATEgg FIBERSpinach CARBRice FIBERBeans
=================
Output :
--FATOil:FATCheese:FATEgg--FIBERSpinach:CARBRice:FIBERBeans
=================
*/

/*
=================
Test Case 3   :
=================
Input :
12
4
FATOil FIBERSpinach CARBRice FATCheese FIBERBeans FATEgg FIBERBroccoli CARBPotato CARBCorn FATOlive FIBERCarrot CARBBeetroot
=================
Output :
-----FATOil:FIBERSpinach:FATCheese:FATEgg--CARBRice:FIBERBeans:CARBPotato:CARBCorn---
=================
*/

/*
=================
Test Case 4   :
=================
Input :
18
4
FIBERBroccoli CARBRice CARBOat FATEgg FATCoconut CARBCorn FIBERBeans FATCheese FATSalmon FIBERCarrot FIBERSpinach CARBQuinoa CARBPotato FIBERPumpkin FATOil FIBERBarley FATOlive CARBWheat
=================
Output :
-----FIBERBroccoli:CARBRice:CARBOat:CARBCorn-FATEgg:FATCoconut:FIBERBeans:FATCheese-----FATSalmon:FIBERCarrot:FIBERSpinach:FIBERPumpkin---CARBQuinoa:CARBPotato:FATOil:CARBWheat
=================
*/

/*
=================
Test Case 5   :
=================
Input :
18
5
FIBERBroccoli FATEgg FIBERPumpkin FATOil CARBPotato FATSalmon CARBWheat FATCheese FIBERSpinach CARBQuinoa CARBOat FATOlive CARBCorn FIBERCarrot CARBRice FATCoconut FIBERBeans FIBERBarley
=================
Output :
-----FIBERBroccoli:FATEgg:FIBERPumpkin:FATOil:FATSalmon---CARBPotato:CARBWheat:FATCheese:FIBERSpinach:CARBQuinoa----CARBOat:FATOlive:CARBCorn:FIBERCarrot:CARBRice---
=================
*/

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberOfDays = in.nextInt();
        int numberOfIngredients = in.nextInt();

        List<String> ingredientList = new ArrayList<>();
        int sameCategoryCountRequired = (int) Math.ceil(numberOfIngredients * 3 / 5d);
        String resultString = "";

        for (int i = 0; i < numberOfDays; i++) {
            String resultStringInternal = "-";
            String ingredientId = in.next();
            ingredientList.add(ingredientId);
            long fiberCount = ingredientList.stream().filter(ing -> ing.contains("FIBER")).count();
            long fatCount = ingredientList.stream().filter(ing -> ing.contains("FAT")).count();
            long carbCount = ingredientList.stream().filter(ing -> ing.contains("CARB")).count();

            // System.out.println("Ingredient List : " + ingredientList.toString());

            // Only one major would present at a time as at the end the cooked ingredients are removed from ingredientList.
            boolean isFiberMajor = fiberCount >= sameCategoryCountRequired && ingredientList.size() >= numberOfIngredients;
            boolean isFatMajor = fatCount >= sameCategoryCountRequired && ingredientList.size() >= numberOfIngredients;
            boolean isCarbMajor = carbCount >= sameCategoryCountRequired && ingredientList.size() >= numberOfIngredients;

            if (isFiberMajor) {
                resultStringInternal = cook(ingredientList,numberOfIngredients, "FIBER");
            }

            if (isFatMajor) {
                resultStringInternal = cook(ingredientList,numberOfIngredients, "FAT");
            }

            if (isCarbMajor) {
                resultStringInternal = cook(ingredientList,numberOfIngredients, "CARB");
            }

            resultString += resultStringInternal;
        }
        System.out.println(resultString);
    }

    private static String cook(List<String> ingredientList, int numberOfIngredients, String category) {
        String resultStringInternal = "";
        String[] resultArr = new String[ingredientList.size()];

        // Filling up result array with primary ingredient.
        for (int j = 0; j < ingredientList.size(); j++) {
            if (ingredientList.get(j).contains(category)) {
                resultArr[j] = ingredientList.get(j);
            }
        }

        // Filling up result array with non primary ingredient upto numberOfIngredients.
        int k = 0;
        while (nonNullCount(resultArr) < numberOfIngredients) {
            if (resultArr[k] == null) {
                resultArr[k] = ingredientList.get(k);
            }
            k++;
        }

        // Removing null entries from result array.
        List<String> resultList = Stream.of(resultArr).collect(Collectors.toList());
        while (resultList.remove(null)) ;

        // Computing result string from result array.
        if (resultList.size() > 0) {
            resultStringInternal = String.join(":", resultList);
        }

        // Deleting used ingredients from ingredientList
        ingredientList.removeAll(resultList);

        return resultStringInternal;
    }

    private static int nonNullCount(String[] arr) {
        int count = 0;
        for (int i=0; i<arr.length; i++) {
            if (arr[i] != null) {
                count++;
            }
        }
        return count;
    }
}
