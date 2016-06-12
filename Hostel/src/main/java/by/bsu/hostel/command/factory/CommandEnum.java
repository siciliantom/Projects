package by.bsu.hostel.command.factory;

import by.bsu.hostel.command.additional.BackCommand;
import by.bsu.hostel.command.additional.ChangeLangCommand;
import by.bsu.hostel.command.additional.LogoutCommand;
import by.bsu.hostel.command.crucial.*;
import by.bsu.hostel.command.show.ShowBookingFormCommand;
import by.bsu.hostel.command.show.ShowRegistrationFormCommand;


/**
 * Created by Kate on 05.02.2016.
 *
 * Enum with all command types
 *
 * @author Kate
 * @version 1.0
 */
public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    DELETE_APPLICATION {
        {
            this.command = new DeleteApplicationCommand();
        }
    },
    ORDER {
        {
            this.command = new BookCommand();
        }
    },
    RETRIEVE_APPROPRIATE_ROOMS {
        {
            this.command = new RetrieveAppropriateRoomsCommand();
        }
    },
    RETRIEVE_CLIENTS {
        {
            this.command = new RetrieveClientsCommand();
        }
    },
    CONFIRM {
        {
            this.command = new ConfirmCommand();
        }
    },
    BACK {
        {
            this.command = new BackCommand();
        }
    },
    BAN {
        {
            this.command = new BanCommand();
        }
    },
    CHANGE_LANG {
        {
            this.command = new ChangeLangCommand();
        }
    },
    SHOW_REG_FORM {
        {
            this.command = new ShowRegistrationFormCommand();
        }
    },
    SHOW_HISTORY {
        {
            this.command = new RetrieveHistoryCommand();
        }
    },
    SHOW_ORDER_FORM {
        {
            this.command = new ShowBookingFormCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
