<template>
    <h2>Fabricantes</h2>

    <!-- Manufacturer Table -->
    <Table :columns="columns_manufacturer" :data-source="manufacturerItems" bordered @change="handleChange">

      <template
          #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
      >
        <template v-if="['name'].includes(column.dataIndex)">
          <div style="padding: 8px">
            <a-input
                ref="searchInput"
                :placeholder="`Buscar por ${filter_options_categories.filter((item) => item.key == column.dataIndex)[0].name}`"
                :value="selectedKeys[0]"
                style="width: 188px; margin-bottom: 8px; display: block"
                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
            />
            <a-button
                type="primary"
                size="small"
                style="width: 90px; margin-right: 8px"
                @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
            >
              <template #icon><SearchOutlined /></template>
              Search
            </a-button>
            <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
              Reset
            </a-button>
          </div>
        </template>
      </template>
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
import { useManufacturerStore } from "../stores/manufacturerStore.ts";
import {onBeforeMount, reactive, ref, UnwrapRef} from "vue";
import {Table, Input, Typography, Popconfirm, TableProps} from "ant-design-vue";
import { Manufacturer } from "../model/manufacturer.ts";
import {EditOutlined, DeleteOutlined, PlusOutlined, SearchOutlined} from "@ant-design/icons-vue";
import {columns_manufacturer, filter_options_categories, filter_options_manufacturer} from "../utils/tablesCols.ts";

const manufacturerStore = useManufacturerStore();
const manufacturerItems = ref<Manufacturer[]>([]);
const modalVisible = ref<boolean>(false);

async function getAllManufacturers() {
  const result = await manufacturerStore.getAllManufacturers();
  manufacturerItems.value = result;
}

async function getAllManufacturersByFieldName(field: String, name: String){
  const result = await manufacturerStore.getAllByName(field, name);
  manufacturerItems.value = result;
}

async function orderByField(field: String, direction: String){
  const result = await manufacturerStore.orderByField(field, direction);
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

const editableData: UnwrapRef<Record<number, Partial<Manufacturer>>> = reactive({});

const edit = (key: string) => {
  editableData[key] = { ...manufacturerItems.value.find((item) => parseInt(key) === item.id) };
};

const save = (key: string) => {
  Object.assign(
      manufacturerItems.value.find(
          (item) => parseInt(key) === item.id
      ),
      editableData[key]
  );
  editManufacturerById(editableData[parseInt(key)])
  delete editableData[parseInt(key)];
};

const cancel = (key: string) => {
  delete editableData[parseInt(key)];
};

const setModalVisible = (open: boolean) => {
  modalVisible.value = open;
};

const formStateManufacturer = reactive<Manufacturer>({
  id: 0,
  name: ''
});

const state = reactive({
  searchText: '',
  searchedColumn: '',
});

const searchInput = ref();

const handleSearch = (selectedKeys, confirm, dataIndex) => {
  confirm();
  state.searchText = selectedKeys[0];
  state.searchedColumn = dataIndex;
  switch (dataIndex) {
    case 'name':
      const field = filter_options_manufacturer.filter((item) => item.key == dataIndex)[0].req
      getAllManufacturersByFieldName(field, selectedKeys[0])
      break
  }
}

const handleChange: TableProps['onChange'] = (pagination, filters, sorter) => {
  try {
    let field = sorter['columnKey']
    let order = sorter['order'].split('end')[0]

    orderByField(field, order)
  } catch (e) {console.log(e)}
};


const handleReset = clearFilters => {
  clearFilters({ confirm: true });
  state.searchText = '';
  getAllManufacturers()
};

const handleOkButtonModal = () => {
  createManufacturer()
  setModalVisible(false)
}

</script>

<style scoped>
.editable-row-operations {
  display: flex;
  justify-content: space-around;
}

.editable-row-operations a {
  margin-right: 8px;
}

.editable_operations {
  display: flex;
}
</style>
