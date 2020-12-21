package ro.fasttrackit.homework.curs20.frontend.editor;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import ro.fasttrackit.homework.curs20.frontend.repository.TransactionRepository;
import ro.fasttrackit.homework.curs20.frontend.entities.Transaction;

@SpringComponent
@UIScope
public class TransactionEditor extends VerticalLayout implements KeyNotifier {

    private final TransactionRepository repository;
    private Transaction transaction;

    TextField product = new TextField("Product");
    TextField type = new TextField("Type");
    TextField amount = new TextField("Amount");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Transaction> binder = new Binder<>(Transaction.class);
    private ChangeHandler changeHandler;

    @Autowired
    public TransactionEditor(TransactionRepository repository) {
        this.repository = repository;

        add(product, type, amount, actions);
        binder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());
        delete.addClickListener(e->delete());
        cancel.addClickListener(e -> editTransaction(transaction));
        setVisible(false);

    }

    public final void editTransaction(Transaction c) {
        if (c == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = c.getId() != null;
        if (persisted) {
            transaction = repository.findById(c.getId()).get();
        } else {
            transaction = c;
        }

        cancel.setVisible(persisted);
        binder.setBean(transaction);
        setVisible(true);
        product.focus();
    }

    private void delete() {
        repository.delete(transaction);
        changeHandler.onChange();
    }

    private void save() {
        repository.save(transaction);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
