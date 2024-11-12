package ch.admin.bar.siardsuite.model;

import ch.admin.bar.siard2.api.ext.form.FormData;
import ch.admin.bar.siardsuite.model.database.DatabaseTable;
import ch.admin.bar.siardsuite.ui.component.rendering.model.RenderableForm;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TreeItem;
import lombok.*;

import java.util.Arrays;
import java.util.List;


/**
 * <p>노드에 속성을 추가하기 위한 래퍼 클래스</p>
 * 체크박스에 체크된 속성을 유지하기 위한 {@link this#selected} 속성 추가
 */
@Getter
@AllArgsConstructor
@Builder
public class TreeAttributeWrapper {

    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final DatabaseAttribute databaseAttribute;
    private final DatabaseTable databaseTable;

    private final boolean shouldHaveCheckBox; // 체크박스가 필요한 노드인지
    private final boolean transferable; // 체크 여부가 필요한 노드인지
    private final boolean shouldPropagate; // 부모 노드와 자식 노드의 값 연동 여부

    @NonNull
    private final DisplayableText name;
    @NonNull
    private final DisplayableText viewTitle;
    @NonNull
    private final RenderableForm<?> renderableForm;

    private final FormData formData = new FormData();

    private boolean checkedForDownload;

    private final boolean columnSelectable;

    private final long size; // 테이블 예상 바이트 크기
    private final String formattedSize; // 테이블 사이즈

    @Override
    public String toString() {
        return name.getText();
    }

    public void markAsCheckedForDownload() { checkedForDownload = true; }

    public boolean notCheckedForDownload() { return !checkedForDownload; }

    public boolean shouldPropagate() {return this.shouldPropagate;}

    public boolean shouldHaveCheckBox() {
        return this.shouldHaveCheckBox;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {return selected.get(); }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public String getDisplayName() {
        return this.name.getText();
    }
    public boolean isTransferable(DatabaseAttribute attribute) { return DatabaseAttribute.isToBeTransferred(attribute); }

    public boolean isSchemaAttr() { return this.databaseAttribute == DatabaseAttribute.SCHEMA; }
    public boolean isTableAttr() { return this.databaseAttribute == DatabaseAttribute.TABLE; }
    public boolean isColumnAttr() { return this.databaseAttribute == DatabaseAttribute.COLUMN; }
    public boolean isRecordAttr() { return this.databaseAttribute == DatabaseAttribute.RECORD; }

    public boolean isTableForSftpConnection() {return isColumnSelectable() && isTableAttr();}
    public boolean isSelectedColumn() {return isColumnSelectable() && isSelected() && isColumnAttr();}

    public void setTableToDefault() {
        if (this.isRecordAttr()) this.getDatabaseTable().getTable().setRowsToDefault();
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
        RECORD("record", false)
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
