package seedu.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.contact.DetailType;
import seedu.exception.InvalidFlagException;
import seedu.exception.MissingArgException;
import seedu.exception.MissingDetailException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddContactParserTest {
    private AddContactParser addContactParser;
    private String[] inputContactDetails;
    private String inputDetail;
    private String inputUserInput;
    private String[] expectedOutput;
    private String[] actualOutput;
    public static final int NUMBER_OF_DETAILS = 2;

    @BeforeEach
    public void setUp() {
        addContactParser = new AddContactParser();
        inputContactDetails = new String[NUMBER_OF_DETAILS];
    }

    @Test
    void parseContactDetails_inputsWithIrregularSpacing_expectCorrectDetails() throws InvalidFlagException,
            MissingDetailException, MissingArgException {
        inputUserInput = "add       -n   andre   -g ng-andre   ";
        actualOutput = addContactParser.parseContactDetails(inputUserInput);
        expectedOutput = new String[]{"andre", "ng-andre"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    void parseContactDetails_onlyAddInput_expectException() {
        inputUserInput = "   add  ";
        assertThrows(MissingArgException.class, () -> addContactParser.parseContactDetails(inputUserInput));
    }

    @Test
    void parseContactDetails_missingAllArguments_expectException() {
        inputUserInput = "   add  -n   -g  ";
        assertThrows(MissingDetailException.class, () -> addContactParser.parseContactDetails(inputUserInput));
    }

    @Test
    void parseContactDetails_invalidFlags_expectException() {
        inputUserInput = "   add  -p pinterest  -f facebook ";
        assertThrows(InvalidFlagException.class, () -> addContactParser.parseContactDetails(inputUserInput));
    }

    @Test
    void parseContactDetails_missingDetails_expectException() {
        inputUserInput = "   add  -p  -f  ";
        assertThrows(MissingDetailException.class, () -> addContactParser.parseContactDetails(inputUserInput));
    }

    @Test
    void parseDetail_emptyDetail_expectException() {
        // Upon user input being split, input detail may contain empty details
        inputDetail = "n  ";
        assertThrows(MissingDetailException.class,
            () -> addContactParser.parseDetail(inputContactDetails, inputDetail));
    }

    @Test
    void parseDetail_invalidFlagUsed_expectException() {
        // As of V1.0, only GitHub (-g) and Name (-n) flags are used
        inputDetail = "f facebook";
        assertThrows(InvalidFlagException.class, () -> addContactParser.parseDetail(inputContactDetails, inputDetail));
    }

    @Test
    void parseDetail_inputsWithTrailingSpaces_expectNewContactDetail() throws InvalidFlagException,
            MissingDetailException {
        inputDetail = "n             andre";
        addContactParser.parseDetail(inputContactDetails, inputDetail);
        assertEquals("andre", inputContactDetails[DetailType.NAME.getIndex()]);
    }

    @Test
    void parseDetail_inputsWithSpaceInDetail_expectNewContactDetail() throws InvalidFlagException,
            MissingDetailException {
        inputDetail = "n Marcus Bory";
        addContactParser.parseDetail(inputContactDetails, inputDetail);
        assertEquals("Marcus Bory", inputContactDetails[DetailType.NAME.getIndex()]);
    }
}