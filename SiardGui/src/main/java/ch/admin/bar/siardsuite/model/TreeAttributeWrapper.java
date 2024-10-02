package ch.admin.bar.siardsuite.model;

import ch.admin.bar.siardsuite.model.database.DatabaseTable;
import ch.admin.bar.siardsuite.ui.component.rendering.model.RenderableForm;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.*;

import java.util.Arrays;


/**
 * <p>노드에 속성을 추가하기 위한 래퍼 클래스</p>
 * 체크박스에 체크된 속성을 유지하기 위한 {@link this#selected} 속성 추가
 */
@Value
@Builder
public class TreeAttributeWrapper {

    BooleanProperty selected = new SimpleBooleanProperty(false);
    DatabaseAttribute databaseAttribute;
    DatabaseTable databaseTable;

    boolean shouldHaveCheckBox; // 체크박스가 필요한 노드인지
    boolean transferable; // 체크 여부가 필요한 노드인지
    boolean shouldPropagate; // 부모 노드와 자식 노드의 값 연동 여부

    @NonNull DisplayableText name;
    @NonNull DisplayableText viewTitle;
    @NonNull RenderableForm<?> renderableForm;

    long size; // 테이블 예상 바이트 크기
    String formattedSize; // 테이블 사이즈

    @Override
    public String toString() {
        return name.getText();
    }

    public boolean shouldPropagate() {
        return this.shouldPropagate;
    }

    public boolean shouldHaveCheckBox() {
        return this.shouldHaveCheckBox;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public String getDisplayName() {
        return this.name.getText();
    }

    public boolean isTransferable(DatabaseAttribute attribute) {
        return DatabaseAttribute.isToBeTransferred(attribute);
    }

    @Getter
    @RequiredArgsConstructor
    public enum DatabaseAttribute {
        SCHEMA_TITLE("schema_title", true),
        SCHEMA("schema", false),
        VIEW_TITLE("view_title", true),
        VIEW("view", false),
        TABLE_TITLE("table_title", true),
        TABLE("table", false),
        COLUMN_TITLE("column_title", true),
        COLUMN("column", false),
        ;

        final String attribute;
        final boolean isMetaData;

        public static boolean isToBeTransferred(DatabaseAttribute attribute) {
            return Arrays.stream(DatabaseAttribute.values())
                    .filter(attr -> !attr.isMetaData)
                    .anyMatch(attr -> attr == attribute);
        }
    }

}
