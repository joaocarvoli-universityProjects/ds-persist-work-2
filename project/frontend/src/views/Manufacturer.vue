<template>
    <h2>Manufacturer</h2>

    <!-- Manufacturer Table -->
    <Table :columns="columns_manufacturer" :data-source="manufacturerItems" bordered>
      <template #bodyCell="{ column, text, record }">
        <template v-if="['name'].includes(column.dataIndex)">
          <div>
            <Input
                v-if="editableData[record.id]"
                v-model:value="editableData[record.id][column.dataIndex]"
                style="margin: -5px 0"
            />
            <template v-else>
              {{ text }}
            </template>
          </div>
        </template>

        <template v-else-if="column.dataIndex === 'operation'">
          <div class="editable-row-operations">
            <span v-if="editableData[record.id]">
              <Typography.Link @click="save(record.id)">Save</Typography.Link>
              <Popconfirm title="Sure to cancel?" @confirm="cancel(record.id)">
                <a>Cancel</a>
              </Popconfirm>
            </span>
            <span v-else class="editable_operations">
              <a @click="edit(record.id)"><EditOutlined /></a>
              <Popconfirm title="Sure to delete?" @confirm="removeManufacturerById(record.id)">
                <a><DeleteOutlined /></a>
              </Popconfirm>
            </span>
          </div>
        </template>
      </template>
    </Table>

  <!-- Floating Button and Modal -->
    <div id="components-modal-demo-position">
      <a-float-button @click="setModalVisible(true)">
        <template #tooltip>
          <div>Display Modal</div>
        </template>
        <template #icon>
          <PlusOutlined />
        </template>
      </a-float-button>

      <a-modal v-model:open="modalVisible" title="Manufacturer Registration" centered @ok="handleOkButtonModal">
        <a-form>
          <a-form-item
              label="Name"
              name="name">
            <a-input v-model:value="formStateManufacturer.name" />
          </a-form-item>
        </a-form>
      </a-modal>
    </div>
</template>

<script setup lang="ts">
import { useManufacturerStore } from "../stores/manufacturer.ts";
import {onBeforeMount, reactive, ref, UnwrapRef} from "vue";
import { Table, Input, Typography, Popconfirm } from "ant-design-vue";
import { Manufacturer } from "../model/Manufacturer.ts";
import { EditOutlined, DeleteOutlined, PlusOutlined } from "@ant-design/icons-vue";
import { columns_manufacturer } from "../utils/tables_cols.ts";

const manufacturerStore = useManufacturerStore();
const manufacturerItems = ref<Manufacturer[]>([]);
const modalVisible = ref<boolean>(false);

async function getAllManufacturers() {
  const result = await manufacturerStore.getAllManufacturers();
  manufacturerItems.value = result;
}

async function createManufacturer(){
  const response = await manufacturerStore.createManufacturer(formStateManufacturer)
  if(response.success) await getAllManufacturers()
}

async function editManufacturerById(editableData: Record<string, Partial<Manufacturer>>){
  const manufacturer = (editableData as unknown) as Manufacturer
  const response = await manufacturerStore.editManufacturerById(manufacturer);
  if(response.success) await getAllManufacturers()
}

async function removeManufacturerById(manufacturerIdData: string) {
  const manufacturerId = parseInt(manufacturerIdData);
  const response = await manufacturerStore.removeManufacturerById(manufacturerId);
  if(response.success) await getAllManufacturers()
}

onBeforeMount(() => {
  getAllManufacturers();
});

const editableData: UnwrapRef<Record<string, Partial<Manufacturer>>> = reactive({});

const edit = (key: string) => {
  editableData[key] = { ...manufacturerItems.value.find((item) => key === item.id) };
};

const save = (key: string) => {
  Object.assign(
      manufacturerItems.value.find(
          (item) => key === item.id
      ),
      editableData[key]
  );
  editManufacturerById(editableData[key])
  delete editableData[key];
};

const cancel = (key: string) => {
  delete editableData[key];
};

const setModalVisible = (open: boolean) => {
  modalVisible.value = open;
};

const formStateManufacturer = reactive<Manufacturer>({
  id: '',
  name: ''
});

const handleOkButtonModal = () => {
  createManufacturer()
  setModalVisible(false)
}

</script>

<style scoped>
.editable-row-operations {
  display: flex;
  gap: 8px;
}

.editable-row-operations a {
  margin-right: 8px;
}

.editable_operations {
  display: flex;
  gap: 15px;
  margin-left: 5px;
}
</style>
