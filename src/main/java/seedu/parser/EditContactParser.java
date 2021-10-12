package seedu.parser;

import seedu.exception.ForbiddenDetailException;
import seedu.exception.InvalidEmailException;
import seedu.exception.InvalidFlagException;
import seedu.exception.InvalidGithubUsernameException;
import seedu.exception.InvalidLinkedinUsernameException;
import seedu.exception.InvalidNameException;
import seedu.exception.InvalidTelegramUsernameException;
import seedu.exception.InvalidTwitterUsernameException;
import seedu.exception.MissingArgException;
import seedu.exception.MissingDetailException;

public class EditContactParser extends ContactParser {
    public static final String BUFFER = " ";

    public String[] parseContactDetails(String userInput)
            throws InvalidFlagException, MissingDetailException, MissingArgException, ForbiddenDetailException,
            InvalidNameException, InvalidGithubUsernameException, InvalidTelegramUsernameException,
            InvalidLinkedinUsernameException, InvalidTwitterUsernameException, InvalidEmailException {
        String[] inputDetails = userInput.split(" ", NUMBER_OF_EDIT_ARGS);
        if (inputDetails.length < NUMBER_OF_EDIT_ARGS) {
            //if arguments are missing e.g. edit 2
            throw new MissingArgException();
        }
        assert (inputDetails.length == NUMBER_OF_EDIT_ARGS);
        String[] contactDetails = new String[NUMBER_OF_FIELDS]; //initialise null array of strings
        //buffer is used to ensure first flag can be read
        String[] destructuredInputs = (BUFFER + inputDetails[USER_INFO_INDEX]).split(DETAIL_SEPARATOR);
        //handles illegal input "edit 0 -" and "edit 0 [invalid string]"
        //valid input will take the form of [, -flag input] so min length should be 2
        if (destructuredInputs.length < 2) {
            throw new MissingArgException();
        }
        for (int i = CONTACT_PARAMS_START_INDEX; i < destructuredInputs.length; i++) {
            parseDetail(contactDetails, destructuredInputs[i]);
        }
        return contactDetails;
    }

    public int getIndex(String input) throws MissingArgException {
        String[] destructuredInputs = input.split(" ", NUMBER_OF_EDIT_ARGS);
        if (destructuredInputs.length < NUMBER_OF_EDIT_ARGS) {
            //if arguments are missing e.g. edit 2
            throw new MissingArgException();
        }
        assert (destructuredInputs.length == NUMBER_OF_EDIT_ARGS);
        return Integer.parseInt(destructuredInputs[EDIT_USER_INDEX]);
    }
}
