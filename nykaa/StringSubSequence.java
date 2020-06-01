import java.util.*;

class StringSubSequence {
    public static void main(String[] args) {
        String testString = "aabb";
        int ts = getCountForList(testString);
        int tds = getCountForSet(testString);

        System.out.println(Math.abs(tds - ts));
    }

    private static Integer getCountForList(String str) {
        if (str == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < str.length() - i; j++) {
                String subStr = str.substring(j, j + (i + 1));
                count += subStr.chars().distinct().count();
            }
        }
        return count;
    }

    private static Integer getCountForSet(String str) {
        if (str == null) {
            return 0;
        }
        int count = 0;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < str.length() - i; j++) {
                String subStr = str.substring(j, j + (i + 1));
                if (!set.contains(subStr)) {
                    count += subStr.chars().distinct().count();
                }
                set.add(subStr);
            }
        }
        return count;
    }
}