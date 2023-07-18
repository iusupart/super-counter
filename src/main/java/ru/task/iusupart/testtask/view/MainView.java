package ru.task.iusupart.testtask.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import ru.task.iusupart.testtask.model.Value;
import ru.task.iusupart.testtask.repository.ValueRepository;

import java.util.Date;
import java.util.Objects;

/**
 * Main view for the Super Counter application.
 * Provides user interaction to increment a count value, display the current value,
 * and save the value in the database.
 * Extends VerticalLayout to align components vertically in the browser window.
 * Data from the user input is stored in a repository using Spring Data JPA.
 */
@Slf4j
@Route("")
public class MainView extends VerticalLayout {
    private TextField textField;
    private Button button;
    private Integer value;
    private Binder<Integer> binder;
    private ValueRepository valueRepository;

    /**
     * Constructor for the MainView class.
     * @param valueRepository The ValueRepository for data storage.
     */
    public MainView(ValueRepository valueRepository) {
        this.valueRepository = valueRepository;

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("СУПЕР СЧЕТЧИК");
        title.getStyle().set("text-align", "center");

        textField = new TextField();
        button = new Button("Увеличить на 1");

        Value dbValueInit = valueRepository.findById(1).orElse(new Value());
        if (Objects.nonNull(dbValueInit) && Objects.nonNull(dbValueInit.getCount())) {
            value = dbValueInit.getCount();
            textField.setValue(value.toString());
            log.info("Initial value loaded from database: {}", value);
        } else {
            value = 1;
            textField.setValue("1");
            log.info("No initial value found in database, setting to 1");
        }

        binder = new Binder<>();
        StringToIntegerConverter converter = new StringToIntegerConverter("Неправильный формат числа");

        binder.forField(textField)
                .withConverter(converter)
                .bind(
                        integer -> value,
                        (integer, currentValue) -> {
                            value = currentValue;
                            textField.setValue(value.toString());
                            log.info("Value changed: {}", value);
                        }
                );

        textField.setValue(value.toString());
        textField.getElement().addEventListener("input", event -> {
            textField.setHelperText("Нажми Enter чтобы изменить");
        });

        textField.addKeyDownListener(Key.ENTER, event -> {
            if (!binder.validate().isOk()) {
                textField.setInvalid(true);
                textField.setErrorMessage("Неправильный формат");
                log.error("Incorrect number format in text field");
            } else {
                binder.setBean(Integer.parseInt(textField.getValue()));
                textField.setInvalid(false);
                textField.setErrorMessage(null);

                Value dbValue = valueRepository.findById(1).orElse(new Value());
                dbValue.setCount(value);
                dbValue.setLastModifiedTime(new Date());
                valueRepository.save(dbValue);

                log.info("Value saved to the database: {}", value);
            }
            textField.setHelperText(null);
        });

        button.addClickListener(e -> {
            value++;
            binder.setBean(value);
            Value dbValue = valueRepository.findById(1).orElse(new Value());
            dbValue.setCount(value);
            dbValue.setLastModifiedTime(new Date());
            valueRepository.save(dbValue);
            log.info("Value saved to the database: {}", value);
        });

        add(title, textField, button);
    }
}