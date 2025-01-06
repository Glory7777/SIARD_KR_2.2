package ch.admin.bar.siardsuite.ui.presenter.archive.browser;

import ch.admin.bar.siard2.api.MetaParameter;
import ch.admin.bar.siard2.cmd.utils.ByteFormatter;
import ch.admin.bar.siardsuite.ui.common.Icon;
import ch.admin.bar.siardsuite.ui.component.rendering.model.RenderableForm;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms.*;
import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import ch.admin.bar.siardsuite.model.database.DatabaseAttribute;
import ch.admin.bar.siardsuite.model.database.DatabaseColumn;
import ch.admin.bar.siardsuite.model.database.DatabaseSchema;
import ch.admin.bar.siardsuite.model.database.DatabaseTable;
import ch.admin.bar.siardsuite.model.database.DatabaseType;
import ch.admin.bar.siardsuite.model.database.DatabaseView;
import ch.admin.bar.siardsuite.model.database.Privilige;
import ch.admin.bar.siardsuite.model.database.Routine;
import ch.admin.bar.siardsuite.model.database.SiardArchive;
import ch.admin.bar.siardsuite.model.database.User;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKeyArg;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

import static ch.admin.bar.siardsuite.model.TreeAttributeWrapper.DatabaseAttribute.*;

@Builder
@RequiredArgsConstructor
public class TreeBuilder {

    private static final I18nKey ROOT_ELEMENT_NAME = I18nKey.of("tableContainer.title.siardFile");

    private static final I18nKeyArg<Number> PRIVILEGES_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.priviliges");
    private static final I18nKey PRIVILEGES_VIEW_TITLE = I18nKey.of("tableContainer.title.priviliges");

    private static final I18nKeyArg<Number> USERS_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.users");
    private static final I18nKey USERS_VIEW_TITLE = I18nKey.of("tableContainer.title.users");

    private static final I18nKeyArg<Number> SCHEMAS_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.schemas");
    private static final I18nKey SCHEMAS_VIEW_TITLE = I18nKey.of("tableContainer.title.schemas");
    private static final I18nKey SCHEMA_VIEW_TITLE = I18nKey.of("tableContainer.title.schema");

    private static final I18nKeyArg<Number> TYPES_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.types");
    private static final I18nKey TYPES_VIEW_TITLE = I18nKey.of("treeContent.types.title");
    private static final I18nKey TYPE_VIEW_TITLE = I18nKey.of("tableContainer.title.type");

    private static final I18nKey ATTRIBUTE_VIEW_TITLE = I18nKey.of("tableContainer.title.attribute");

    private static final I18nKeyArg<Number> ROUTINES_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.routines");
    private static final I18nKey ROUTINES_VIEW_TITLE = I18nKey.of("tableContainer.title.routines");
    private static final I18nKey ROUTINE_VIEW_TITLE = I18nKey.of("tableContainer.title.routine");

    private static final I18nKeyArg<Number> PARAMETERS_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.parameters");
    private static final I18nKey PARAMETER_VIEW_TITLE = I18nKey.of("tableContainer.title.parameter");

    private static final I18nKeyArg<Number> VIEWS_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.views");
    private static final I18nKey VIEWS_VIEW_TITLE = I18nKey.of("tableContainer.title.views");
    private static final I18nKey VIEW_VIEW_TITLE = I18nKey.of("tableContainer.title.view");

    private static final I18nKeyArg<Number> COLUMNS_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.columns");
    private static final I18nKey COLUMNS_VIEW_TITLE = I18nKey.of("tableContainer.title.columns");
    private static final I18nKey COLUMN_VIEW_TITLE = I18nKey.of("tableContainer.title.column");

    private static final I18nKeyArg<Number> TABLES_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.tables");
    private static final I18nKey TABLES_VIEW_TITLE = I18nKey.of("tableContainer.title.tables");
    private static final I18nKey TABLE_VIEW_TITLE = I18nKey.of("tableContainer.title.table");

    private static final I18nKeyArg<Number> ROWS_ELEMENT_NAME = I18nKeyArg.of("archive.tree.view.node.rows");
    private static final I18nKey ROWS_VIEW_TITLE = I18nKey.of("tableContainer.title.data");

    @Getter private final SiardArchive siardArchive;
    private final boolean readonly;
    private final boolean columnSelectable;
    private final String searchTerm;


    //  1.  RootItem
    public TreeItem<TreeAttributeWrapper> createRootItem() {
        val rootItem = new TreeItem<>(
                TreeAttributeWrapper.builder()
                        .name(DisplayableText.of(this.siardArchive.getName().orElse("")))
                        .viewTitle(DisplayableText.of(ROOT_ELEMENT_NAME))
                        .renderableForm(MetadataDetailsForm.create(siardArchive)
                                .toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                        .shouldPropagate(true)
                        .build(),
                new ImageView(Icon.DB.toResizedImageOfHeight(16)));

        rootItem.setExpanded(true);
        rootItem.getChildren().add(createItemForSchemas());

        if (!siardArchive.users().isEmpty()) {
            rootItem.getChildren().add(createItemForUsers(siardArchive.users()));
        }

        if (!siardArchive.priviliges().isEmpty()) {
            rootItem.getChildren().add(createItemForPrivileges(siardArchive.priviliges()));
        }

        return rootItem;
    }

    public TreeItem<TreeAttributeWrapper> customCreateRootItem() {
        val rootItem = new TreeItem<>(
                TreeAttributeWrapper.builder()
                        .name(DisplayableText.of(this.siardArchive.getName().orElse("")))
                        .viewTitle(DisplayableText.of(ROOT_ELEMENT_NAME))
                        .renderableForm(MetadataDetailsForm.create(siardArchive)
                                .toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                        .shouldPropagate(true)
                        .build(),
                new ImageView(Icon.DB.toResizedImageOfHeight(16)));

        rootItem.setExpanded(true);
        rootItem.getChildren().add(customCreateItemForSchemas());

        if (!siardArchive.users().isEmpty()) {
            rootItem.getChildren().add(createItemForUsers(siardArchive.users()));
        }

        if (!siardArchive.priviliges().isEmpty()) {
            rootItem.getChildren().add(createItemForPrivileges(siardArchive.priviliges()));
        }

        return rootItem;
    }



    //  createRootItemWithSelectedSchemas 랑 createItemForSchemasAndCountTableSize 는
    //  columnFileDownLoadBrowser 을 위한 메서드, 본래
    //  createRootItemWithSelectedSchemas - createItemForSchemas 였으나 formatted[]Size 값의 변경이 있으므로
    // 그냥 복사해서 메서드명 변경함
    /**2. createRootItemWithSelectedSchemas
     * create root item with schemas and entities chosen in the previous step
     *
     * @return
     */
    public TreeItem<TreeAttributeWrapper> createRootItemWithSelectedSchemas() {
        val rootItem = new TreeItem<>(
                TreeAttributeWrapper.builder()
                        .name(DisplayableText.of(this.siardArchive.getName().orElse("")))
                        .viewTitle(DisplayableText.of(ROOT_ELEMENT_NAME))
                        .renderableForm(MetadataDetailsForm.create(siardArchive)
                                .toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                        .shouldPropagate(true)
                        .build(),
                new ImageView(Icon.DB.toResizedImageOfHeight(16)));

        rootItem.setExpanded(true);
        rootItem.getChildren().add(createItemForSchemasAndCountTableSize());

        return rootItem;
    }


    private TreeItem<TreeAttributeWrapper> createItemForSchemasAndCountTableSize() {
        Set<String> schemaSet = siardArchive.getArchive().getSelectedSchemaTableMap().keySet();
        val schemas = schemaSet.isEmpty() ? this.siardArchive.schemas() : this.siardArchive.schemas().stream().filter(databaseSchema -> schemaSet.contains(databaseSchema.getName())).toList();

           long countTableSize = CustomCheckBoxTreeCell.selectedTotalSize;
           String formattedCountTableSize = ByteFormatter.convertToBestFitUnit(countTableSize);

        long schemaSize = schemas.stream().mapToLong(s -> s.getSchema().getSchemaSize()).sum();

        val schemasItem = new TreeItem<>(
                TreeAttributeWrapper.builder()
                        .name(DisplayableText.of(SCHEMAS_ELEMENT_NAME, schemas.size()))
                        .viewTitle(DisplayableText.of(SCHEMAS_VIEW_TITLE))
                        .renderableForm(MetadataDetailsForm.create(siardArchive).toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                        .databaseAttribute(SCHEMA_TITLE)
                        .shouldPropagate(true)
                        .shouldHaveCheckBox(true)
                        .size(schemaSize)
                        .formattedSize(formattedCountTableSize)
                        .build());

        val schemaItems = schemas.stream()
                .map(this::createItemsForSchema)
                .collect(Collectors.toList());
        schemasItem.getChildren().setAll(schemaItems);

        return schemasItem;
    }



    //4. createItemForPrivileges
    private TreeItem<TreeAttributeWrapper> createItemForPrivileges(List<Privilige> priviliges) {
        return new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(PRIVILEGES_ELEMENT_NAME, priviliges.size()))
                .viewTitle(DisplayableText.of(PRIVILEGES_VIEW_TITLE))
                .renderableForm(PrivilegesOverviewForm.create(siardArchive).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());
    }

    //5. createItemForUsers
    private TreeItem<TreeAttributeWrapper> createItemForUsers(final List<User> users) {
        return new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(USERS_ELEMENT_NAME, users.size()))
                .viewTitle(DisplayableText.of(USERS_VIEW_TITLE))
                .renderableForm(UsersOverviewForm.create(siardArchive).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());
    }


    // 6. createItemForSchemas - createItemForSchemas / customCreateItemForSchemas
    private TreeItem<TreeAttributeWrapper> createItemForSchemas(boolean shouldHaveCheckBox) {
        Set<String> schemaSet = siardArchive.getArchive().getSelectedSchemaTableMap().keySet();
        val schemas = schemaSet.isEmpty()
                ? this.siardArchive.schemas()
                : this.siardArchive.schemas().stream().filter(databaseSchema -> schemaSet.contains(databaseSchema.getName())).toList();

        long schemaSize = schemas.stream().mapToLong(s -> s.getSchema().getSchemaSize()).sum();

        // formattedSchemaSize 설정
        String formattedSchemaSize = shouldHaveCheckBox
                ? ByteFormatter.convertToBestFitUnit(schemaSize) // 체크박스가 있을 경우
                : (schemaSize == 0 ? "" : ByteFormatter.convertToBestFitUnit(schemaSize)); // 체크박스가 없을 경우

        val schemasItem = new TreeItem<>(
                TreeAttributeWrapper.builder()
                        .name(DisplayableText.of(SCHEMAS_ELEMENT_NAME, schemas.size()))
                        .viewTitle(DisplayableText.of(SCHEMAS_VIEW_TITLE))
                        .renderableForm(MetadataDetailsForm.create(siardArchive).toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                        .databaseAttribute(SCHEMA_TITLE)
                        .shouldPropagate(true)
                        .shouldHaveCheckBox(shouldHaveCheckBox) // 체크박스 설정
                        .size(schemaSize)
                        .formattedSize(formattedSchemaSize) // 설정된 formattedSchemaSize 사용
                        .build()
        );

        val schemaItems = schemas.stream()
                .map(shouldHaveCheckBox ? this::createItemsForSchema : this::customCreateItemsForSchema) // 분기 처리
                .collect(Collectors.toList());
        schemasItem.getChildren().setAll(schemaItems);

        return schemasItem;
    }

    // 기존 메서드
    private TreeItem<TreeAttributeWrapper> createItemForSchemas() {
        return createItemForSchemas(true); // 기본 체크박스 활성화
    }
    // 커스텀 메서드
    private TreeItem<TreeAttributeWrapper> customCreateItemForSchemas() {
        return createItemForSchemas(false); // 커스텀 체크박스 비활성화
    }



    // 7. createItemsForSchema
    private TreeItem<TreeAttributeWrapper> createItemsForSchema(DatabaseSchema schema) {

        long schemaSize = schema.getSchema().getSchemaSize();
        String formattedSchemaSize = ByteFormatter.convertToBestFitUnit(schemaSize);

        val schemaItem = new TreeItem<>(
                TreeAttributeWrapper.builder()
                        .name(DisplayableText.of(schema.getName()))
                        .viewTitle(DisplayableText.of(SCHEMA_VIEW_TITLE))
                        .renderableForm(SchemaOverviewForm.create(schema).toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                        .databaseAttribute(SCHEMA)
                        .shouldPropagate(!columnSelectable)
                        .shouldHaveCheckBox(!columnSelectable)
                        .size(schemaSize)
                        .formattedSize(formattedSchemaSize)
                        .build());

        schemaItem.setExpanded(true);

        if (!schema.getTypes().isEmpty()) {
            schemaItem.getChildren().add(createItemForTypes(schema));
        }


        if (!schema.getTables().isEmpty()) {
            schemaItem.getChildren().add(createItemForTables(schema));
        }

        if (!schema.getRoutines().isEmpty()) {
            schemaItem.getChildren().add(createItemForRoutines(schema));
        }

        if (!schema.getViews().isEmpty()) {
            schemaItem.getChildren().add(createItemForViews(schema));
        }


        return schemaItem;
    }

    // 8. createItemsForSchema
    private TreeItem<TreeAttributeWrapper> customCreateItemsForSchema(DatabaseSchema schema) {

        long schemaSize = schema.getSchema().getSchemaSize();
        String formattedSchemaSize = schemaSize == 0 ? "" : ByteFormatter.convertToBestFitUnit(schemaSize);

        val schemaItem = new TreeItem<>(
                TreeAttributeWrapper.builder()
                        .name(DisplayableText.of(schema.getName()))
                        .viewTitle(DisplayableText.of(SCHEMA_VIEW_TITLE))
                        .renderableForm(SchemaOverviewForm.create(schema).toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                        .databaseAttribute(SCHEMA)
                        .shouldPropagate(false)
                        .shouldHaveCheckBox(false)
                        .size(schemaSize)
                        .formattedSize(formattedSchemaSize)
                        .build());

        schemaItem.setExpanded(true);

        if (!schema.getTypes().isEmpty()) {
            schemaItem.getChildren().add(createItemForTypes(schema));
        }

        if (!schema.getTables().isEmpty()) {
            schemaItem.getChildren().add(customCreateItemForTables(schema));
        }

        if (!schema.getRoutines().isEmpty()) {
            schemaItem.getChildren().add(createItemForRoutines(schema));
        }

        if (!schema.getViews().isEmpty()) {
            schemaItem.getChildren().add(customCreateItemForViews(schema));
        }

        return schemaItem;
    }


    // 9. ItemsForSchema 공통 사항
    //1) ItemForTypes
    private TreeItem<TreeAttributeWrapper> createItemForTypes(final DatabaseSchema schema) {
        val types = schema.getTypes();

        val typesItem = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(TYPES_ELEMENT_NAME, types.size()))
                .viewTitle(DisplayableText.of(TYPES_VIEW_TITLE))
                .renderableForm(TypesOverviewForm.create(schema).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());

        val typeItems = types.stream()
                .map(this::createItemsForType)
                .collect(Collectors.toList());

        typesItem.getChildren().addAll(typeItems);

        return typesItem;
    }

    //2) ItemForType
    private TreeItem<TreeAttributeWrapper> createItemsForType(final DatabaseType type) {
        val item = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(type.getName()))
                .viewTitle(DisplayableText.of(TYPE_VIEW_TITLE))
                .renderableForm(TypeDetailsForm.create(type).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());

        val attributeItems = type.getDatabaseAttributes().stream()
                .map(this::createItemsForAttribute)
                .collect(Collectors.toList());

        item.getChildren().addAll(attributeItems);

        return item;
    }

    // 3) ItemsForAttribute
    private TreeItem<TreeAttributeWrapper> createItemsForAttribute(final DatabaseAttribute attribute) {
        return new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(attribute.getName()))
                .viewTitle(DisplayableText.of(ATTRIBUTE_VIEW_TITLE))
                .renderableForm(AttributeDetailsForm.create(attribute).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());
    }

    // 4) ItemForRoutines
    private TreeItem<TreeAttributeWrapper> createItemForRoutines(DatabaseSchema schema) {
        val routines = schema.getRoutines();

        val item = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(ROUTINES_ELEMENT_NAME, routines.size()))
                .viewTitle(DisplayableText.of(ROUTINES_VIEW_TITLE))
                .renderableForm(RoutinesOverviewForm.create(schema).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());

        val routineItems = routines.stream()
                .map(this::createItemsForRoutine)
                .collect(Collectors.toList());

        item.getChildren().addAll(routineItems);

        return item;
    }

    // 5) ItemForRoutine
    private TreeItem<TreeAttributeWrapper> createItemsForRoutine(final Routine routine) {
        val item = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(routine.getName()))
                .viewTitle(DisplayableText.of(ROUTINE_VIEW_TITLE))
                .renderableForm(RoutineOverviewForm.create(routine).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());

        item.getChildren().add(createItemForParameters(routine));

        return item;
    }

    // 6) ItemForParameters
    private TreeItem<TreeAttributeWrapper> createItemForParameters(final Routine routine) {
        val parameters = routine.getParameters();
        val item = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(PARAMETERS_ELEMENT_NAME, parameters.size()))
                .viewTitle(DisplayableText.of(ROUTINE_VIEW_TITLE))
                .renderableForm(RoutineOverviewForm.create(routine).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());

        val parameterItems = parameters.stream()
                .map(this::createItemsForParameter)
                .collect(Collectors.toList());

        item.getChildren().addAll(parameterItems);

        return item;
    }

    // 7) ItemsForParameter
    private TreeItem<TreeAttributeWrapper> createItemsForParameter(final MetaParameter parameter) {
        return new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(parameter.getName()))
                .viewTitle(DisplayableText.of(PARAMETER_VIEW_TITLE))
                .renderableForm(ParameterOverviewForm.create(parameter).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .build());
    }



    private TreeItem<TreeAttributeWrapper> createItemForColumns(DatabaseView view, boolean shouldHaveCheckBox) {
        val item = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(COLUMNS_ELEMENT_NAME, view.columns().size()))
                .viewTitle(DisplayableText.of(COLUMNS_VIEW_TITLE))
                .renderableForm(ViewOverviewForm.create(view.getMetaView()).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .databaseAttribute(COLUMN)
                .transferable(columnSelectable)
                .shouldHaveCheckBox(shouldHaveCheckBox)
                .build());

        // 메서드 참조를 삼항 연산자로 분기 처리
        val columnItems = view.columns().stream()
                .map(shouldHaveCheckBox ? this::createColumnItem : this::customCreateColumnItem)
                .collect(Collectors.toList());

        item.getChildren().addAll(columnItems);

        return item;
    }

    // 기존 메서드
    private TreeItem<TreeAttributeWrapper> createItemForColumns(DatabaseView view) {
        return createItemForColumns(view, columnSelectable);
    }
    // 커스텀 메서드
    private TreeItem<TreeAttributeWrapper> customCreateItemForColumns(DatabaseView view) {
        return createItemForColumns(view, false);
    }




    private TreeItem<TreeAttributeWrapper> createItemForViews(DatabaseSchema schema, boolean isCustom) {

        val views = schema.getViews();
        val item = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(VIEWS_ELEMENT_NAME, views.size()))
                .viewTitle(DisplayableText.of(VIEWS_VIEW_TITLE))
                .renderableForm(ViewsOverviewForm.create(schema).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .databaseAttribute(VIEW_TITLE)
                .build());

        val viewItems = views.stream()
                .map(view -> createItemForView(view, isCustom))

                .collect(Collectors.toList());

        item.getChildren().addAll(viewItems);

        return item;
    }


    private TreeItem<TreeAttributeWrapper> createItemForView(DatabaseView view, boolean isCustom) {

        val item = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(view.name()))
                .viewTitle(DisplayableText.of(VIEW_VIEW_TITLE))
                .renderableForm(ViewOverviewForm.create(view.getMetaView()).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .databaseAttribute(VIEW)
                .build());

        item.getChildren().add(

                isCustom
                        ?  customCreateItemForColumns(view) // 커스텀 동작
                        :  createItemForColumns(view) // 일반 동작
        );

        return item;
    }

    // 기본 메서드
    private TreeItem<TreeAttributeWrapper> createItemForViews(DatabaseSchema schema) {
        return createItemForViews(schema, false);
    }

    // 커스텀 메서드
    private TreeItem<TreeAttributeWrapper> customCreateItemForViews(DatabaseSchema schema) {
        return createItemForViews(schema, true);
    }



    private TreeItem<TreeAttributeWrapper> createItemForTables(DatabaseSchema schema, boolean isCustom) {
        // 선택된 테이블 집합 생성
        List<String> strings = siardArchive.getArchive().getSelectedSchemaTableMap().get(schema.getName());
        Set<String> selectedTableSet = strings != null ? new HashSet<>(strings) : Collections.emptySet();

        // 선택된 테이블 필터링
        val tables = selectedTableSet.isEmpty()
                ? schema.getTables()
                : schema.getTables().stream()
                .filter(databaseTable -> selectedTableSet.contains(databaseTable.getName()))
                .toList();

        // TreeItem 생성
        val tablesItem = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(TABLES_ELEMENT_NAME, tables.size()))
                .viewTitle(DisplayableText.of(TABLES_VIEW_TITLE))
                .renderableForm(
                        SchemaOverviewForm.create(schema).toBuilder()
                                .readOnlyForm(readonly)
                                .build()
                )
                .databaseAttribute(TABLE_TITLE)
                .shouldPropagate(!columnSelectable)
                .build());

        // 테이블 아이템 추가
        tablesItem.getChildren()
                .addAll(tables.stream()
                        .map(table -> isCustom
                                ? customCreateItemForTable(table)  // 커스텀 동작
                                : createItemForTable(table))      // 기본 동작
                        .collect(Collectors.toList()));

        tablesItem.setExpanded(true);

        return tablesItem;
    }

    // 기본 메서드
    private TreeItem<TreeAttributeWrapper> createItemForTables(DatabaseSchema schema) {
        return createItemForTables(schema, false);
    }
    // 커스텀 메서드
    private TreeItem<TreeAttributeWrapper> customCreateItemForTables(DatabaseSchema schema) {
        return createItemForTables(schema, true);
    }



    public TreeItem<TreeAttributeWrapper> createItemForTable(DatabaseTable table, boolean isCustom) {
        // TreeItem 생성
        String countRecords =  " (" + table.getNumberOfRows() + ")";
        DisplayableText tableAndCount = DisplayableText.of(table.getName() + countRecords);

        val tableItem = new TreeItem<>(TreeAttributeWrapper.builder()
                // .name(DisplayableText.of(table.getName()))
                .name(isCustom
                        ? tableAndCount
                        : DisplayableText.of(table.getName()))
                .viewTitle(DisplayableText.of(TABLE_VIEW_TITLE))
                .renderableForm(
                        TableOverviewForm.create(table).toBuilder()
                                .readOnlyForm(readonly)
                                .build())
                .databaseAttribute(TABLE)
                .databaseTable(table)
                .shouldPropagate(false)
                .transferable(!columnSelectable)
                .shouldHaveCheckBox(isCustom ? false : !columnSelectable) // 조건에 따라 체크박스 여부 설정
                .size(table.getTable().getTableSize())
                .formattedSize(table.getTable().getFormattedTableSize())
                .columnSelectable(columnSelectable)
                .build());

        // Row 아이템 추가
        if (!siardArchive.onlyMetaData()) {
            RenderableForm<DatabaseTable> form = RowsOverviewForm.createAndUpdateWithSearchResult(table, searchTerm)
                    .toBuilder()
                    .readOnlyForm(readonly)
                    .build();

            val rowsItem = new TreeItem<>(TreeAttributeWrapper.builder()
                    .renderableForm(form)
                    .name(DisplayableText.of(ROWS_ELEMENT_NAME, table.getNumberOfRows()))
                    .viewTitle(DisplayableText.of(ROWS_VIEW_TITLE))
                    .databaseAttribute(RECORD)
                    .databaseTable(table)
                    .build());

            tableItem.getChildren().add(rowsItem);
        }

        // 컬럼 아이템 추가 (조건에 따라 메서드 선택)
        tableItem.getChildren().add(isCustom ? customCreateColumnItem(table) : createColumnItem(table));

        return tableItem;
    }

    // 일반 테이블 아이템 생성
    public TreeItem<TreeAttributeWrapper> createItemForTable(DatabaseTable table) {
        return createItemForTable(table, false);
    }
    // 커스텀 테이블 아이템 생성
    public TreeItem<TreeAttributeWrapper> customCreateItemForTable(DatabaseTable table) {
        return createItemForTable(table, true);
    }


    private TreeItem<TreeAttributeWrapper> createColumnItem(DatabaseTable table) {
        List<DatabaseColumn> columns = table.getColumns();

        val columnsItem = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(COLUMNS_ELEMENT_NAME, columns.size()))
                .viewTitle(DisplayableText.of(COLUMNS_VIEW_TITLE))
                .renderableForm(TableOverviewForm.create(table).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .databaseAttribute(COLUMN)
                .shouldHaveCheckBox(columnSelectable)
                .transferable(columnSelectable)
                .build());

        val columnItems = columns.stream()
                .map(this::createColumnItem)
                .collect(Collectors.toList());
        columnsItem.getChildren().addAll(columnItems);

        return columnsItem;
    }

    private TreeItem<TreeAttributeWrapper> customCreateColumnItem(DatabaseTable table) {
        List<DatabaseColumn> columns = table.getColumns();

        val columnsItem = new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(COLUMNS_ELEMENT_NAME, columns.size()))
                .viewTitle(DisplayableText.of(COLUMNS_VIEW_TITLE))
                .renderableForm(TableOverviewForm.create(table).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .databaseAttribute(COLUMN)
                .shouldHaveCheckBox(false)
                .transferable(columnSelectable)
                .build());

        val columnItems = columns.stream()
                .map(this::customCreateColumnItem)
                .collect(Collectors.toList());
        columnsItem.getChildren().addAll(columnItems);

        return columnsItem;
    }


    // 공통 메서드
    private TreeItem<TreeAttributeWrapper> createColumnItem(DatabaseColumn column, boolean shouldHaveCheckBox) {
        return new TreeItem<>(TreeAttributeWrapper.builder()
                .name(DisplayableText.of(column.getName()))
                .viewTitle(DisplayableText.of(COLUMN_VIEW_TITLE))
                .renderableForm(ColumnDetailsForm.create(column).toBuilder()
                        .readOnlyForm(readonly)
                        .build())
                .databaseAttribute(COLUMN)
                .shouldHaveCheckBox(shouldHaveCheckBox) // 매개변수로 받은 값 사용
                .transferable(columnSelectable)
                .columnSelectable(columnSelectable)
                .build());
    }

    // 기존 메서드
    private TreeItem<TreeAttributeWrapper> createColumnItem(DatabaseColumn column) {
        return createColumnItem(column, columnSelectable); // 기본 체크박스 설정
    }
    // 커스텀 메서드
    private TreeItem<TreeAttributeWrapper> customCreateColumnItem(DatabaseColumn column) {
        return createColumnItem(column, false); // 체크박스 비활성화
    }

}
