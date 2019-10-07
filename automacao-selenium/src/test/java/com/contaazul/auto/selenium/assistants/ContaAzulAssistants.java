package com.contaazul.auto.selenium.assistants;

import java.awt.AWTException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

public class ContaAzulAssistants {

	public MenuAssistant getMenuAssistant(RemoteWebDriver driver) {
		return new MenuAssistant( driver );
	}

	public ModalAssistant getModalAssistant(RemoteWebDriver driver) {
		return new ModalAssistant( driver );
	}

	public LoginAssistant getLoginAssistant(RemoteWebDriver driver) {
		return new LoginAssistant( driver );
	}

	public AutoCompleteAssistant getAutoCompleteAssistant(RemoteWebDriver driver) {
		return new AutoCompleteAssistant( driver );
	}

	public RegisterAssistant getRegisterAssistant(RemoteWebDriver driver) {
		return new RegisterAssistant( driver );
	}

	public FileUploadAssistant getFileUploadAssistant(RemoteWebDriver driver) {
		return new FileUploadAssistant( driver );
	}

	public UserConfigurationAssistant getUserConfigurationAssistant(RemoteWebDriver driver) {
		return new UserConfigurationAssistant( driver );
	}

	public NotificationAssistant getNotificationAssistant(RemoteWebDriver driver) {
		return new NotificationAssistant( driver );
	}

	public GridAssistant getGridAssistant(RemoteWebDriver driver) {
		return new GridAssistant( driver );
	}

	public DatePickerAssistant getDatePickerAssistant(RemoteWebDriver driver) {
		return new DatePickerAssistant( driver );
	}

	public ListingAssistant getListingAssistant(RemoteWebDriver driver) {
		return PageFactory.initElements( driver, ListingAssistant.class );
	}

	public TableAssistant getTableAssistant(RemoteWebDriver driver) {
		return new TableAssistant( driver );
	}

	public DropdownAssistant getDropdownAssistant(RemoteWebDriver driver) {
		return new DropdownAssistant( driver );
	}

	public FinancialAssistant getFinancialAssistant(RemoteWebDriver driver) {
		return new FinancialAssistant( driver );
	}

	public AdminAssistant getAdminAssistant(RemoteWebDriver driver) {
		return new AdminAssistant( driver );
	}

	public RobotAssistant getRobotAssistant(RemoteWebDriver driver) throws AWTException {
		return new RobotAssistant();
	}

	public DeleteAssistant getDeleteAssistant(RemoteWebDriver driver) {
		return new DeleteAssistant( driver );

	}

	public NewMenuAssistant getNewMenuAssistant(RemoteWebDriver driver) {
		return new NewMenuAssistant( driver );
	}

}
