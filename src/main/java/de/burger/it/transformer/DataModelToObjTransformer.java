package de.burger.it.transformer;

import de.burger.it.DataModel;
import de.burger.it.DataModelObjDataTypes;
import de.burger.it.MyMapper;
import de.burger.it.ValueModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface DataModelToObjTransformer {

    DataModelToObjTransformer INSTANCE = Mappers.getMapper(DataModelToObjTransformer.class);

    DataModelObjDataTypes fromPrimitive(DataModel primitive);

    static ValueModel apply(ValueModel vm) {
        MyMapper mapper = new MyMapper();

        // ValueModel → DataModel
        DataModel primitive = mapper
                .forType(DataModel.class)
                .asSingleType()
                .mapFrom(vm);

        // DataModel → DataModelObjDataTypes via MapStruct
        DataModelObjDataTypes boxed = INSTANCE.fromPrimitive(primitive);

        // DataModelObjDataTypes → ValueModel
        return mapper
                .forType(DataModelObjDataTypes.class)
                .asSingleType()
                .mapToValueModel(boxed);
    }
}
