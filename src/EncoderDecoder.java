import java.util.HashMap;
import java.util.Map;

public class EncoderDecoder {
    //declaration of variable - 2 hashmap, (char : index) - (index : char)
    private Map<Character , Integer> charToIndex; //(char:index)
    private Map<Integer , Character> indexToChar; //(index:char)

    //constructor - construct the hashmap - initialize reference table
    public EncoderDecoder(){
        charToIndex = new HashMap<>();
        indexToChar = new HashMap<>();

        initializeReferenceTable();
    }

    //initialization of reference table - via 1 pass
    private void initializeReferenceTable(){
        //creating string based on reference table in problem statement
        String referenceList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";

        for(int position = 0; position < referenceList.length(); position ++){
            char letter = referenceList.charAt(position);
            //place the letter and index at its respective hashmap
            charToIndex.put(letter, position);
            indexToChar.put(position, letter);
        }
    }

    //encode method
    public String encode(String plainText, char offset) {
        //convert the offset to upper case, so that it will work for both upper and lower case
        char upperCaseOffset = Character.toUpperCase(offset);

        //check for invalid offset
        if (!charToIndex.containsKey(upperCaseOffset) || plainText == null){
            throw new IllegalArgumentException("Encoding failed! Offset provided does not match the reference table list.");
        }

        //convert the offset - a value
        int offsetValue = charToIndex.get(upperCaseOffset);

        //build the string
        StringBuilder encoder = new StringBuilder();
        //add the offset - retrieve the character for the new offset (in its original case)
        encoder.append(offset);

        //loop through string, for each character
        for (char letter : plainText.toCharArray()) {
            //convert the letter to upper case, so that it will work for both upper and lower case
            char upperCaseLetter = Character.toUpperCase(letter);
            //check the hashmap to get the index for this character
            if(charToIndex.containsKey(upperCaseLetter)){
                //get the value (index) and increment the value with offset.
                //% reference list length is used to wrap around the index in circular manner - after end of list -> go back to 0
                int encodedIndex = (charToIndex.get(upperCaseLetter) + offsetValue) % 44;
                //add into the building string the shifted value character - based on the shifted index
                if(Character.isLowerCase(letter)){
                    encoder.append(Character.toLowerCase(indexToChar.get(encodedIndex)));
                }
                else{
                    encoder.append(indexToChar.get(encodedIndex));
                }
            }
            else{
                //add into the building string the original character
                encoder.append(letter);
            }
        }
        //return a converted building string
        return encoder.toString();
    }




    //decode method
    public String decode(String encodedText) {
        //check for invalid conversion request & end function early
        if(encodedText == null || encodedText.length() == 0) {
            throw new IllegalArgumentException("Encoded text cannot be null or empty. Please provide a valid encoded text.");
        }

        //read the decode offset at position 0
        char offset = encodedText.charAt(0);

        //convert the offset to upper case, so that it will work for both upper and lower case
        char upperCaseOffset = Character.toUpperCase(offset);

        //check for invalid offset
        if (!charToIndex.containsKey(upperCaseOffset)) {
            throw new IllegalArgumentException("Encoding failed! Offset provided does not match the reference table list.");
        }

        //convert the offset - a value
        int offsetValue = charToIndex.get(upperCaseOffset);

        //build the string
        StringBuilder decoder = new StringBuilder();

        //loop through string, for each character (start at position 1, skipping the offset character)
        for (int position = 1; position < encodedText.length(); position ++) {

            char upperCaseLetter = Character.toUpperCase(encodedText.charAt(position));

            if(charToIndex.containsKey(upperCaseLetter)){
                //+ 44 is to counteract the -ve value if any
                 int decoderIndex = (charToIndex.get(upperCaseLetter) - offsetValue + 44) % 44;

                if(Character.isLowerCase(encodedText.charAt(position))){
                    decoder.append(Character.toLowerCase(indexToChar.get(decoderIndex)));
                }
                else{
                    decoder.append(indexToChar.get(decoderIndex));
                }
            }
            else{
                //add into the building string the original character
                decoder.append(encodedText.charAt(position));
            }
        }
        //return a converted building string
        return decoder.toString();
    }
}
