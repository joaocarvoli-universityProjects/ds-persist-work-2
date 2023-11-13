<template>

  <h2>Categorias de Produto</h2>

  <!-- Categories Table -->
  <a-table :columns="columns_category" :data-source="categoryItems" bordered @change="handleChange">

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
              <Popconfirm title="Sure to delete?" @confirm="removeCategoryById(record.id)">
                <a><DeleteOutlined /></a>
              </Popconfirm>
            </span>
        </div>
      </template>
    </template>
  </a-table>

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

    <a-modal v-model:open="modalVisible" title="Category Registration" centered @ok="handleOkButtonModal">
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

import {onBeforeMount, reactive, ref, UnwrapRef} from "vue";
import { Input, Typography, Popconfirm, TableProps} from "ant-design-vue";
import {EditOutlined, DeleteOutlined, PlusOutlined, SearchOutlined} from "@ant-design/icons-vue";
import {
  columns_category,
  filter_options_categories,
  filter_options_products,
  filter_options_stock
} from "../utils/tablesCols.ts";
import {useCategoryStore} from "../stores/categoryStore.ts";
import {Category} from "../model/category.ts";

const categoryStore = useCategoryStore();
const categoryItems = ref<Category[]>([]);
const modalVisible = ref<boolean>(false);

async function getAllCategories() {
  const result = await categoryStore.getAllCategories();
  categoryItems.value = result;
}

async function getAllProductsByFieldName(field: String, name: String){
  const result = await categoryStore.getAllByName(field, name);
  categoryItems.value = result;
}

async function orderByField(field: String, direction: String){
  const result = await categoryStore.orderByField(field, direction);
  categoryItems.value = result;
}

async function createCategory(){
  const response = await categoryStore.createCategory(formStateManufacturer)
  if(response.success) await getAllCategories()
}

async function editCategoryById(editableData: Record<string, Partial<Category>>){
  const category = (editableData as unknown) as Category
  const response = await categoryStore.editCategoryById(category);
  if(response.success) await getAllCategories()
}

async function removeCategoryById(categoryIdData: string) {
  const categoryId = parseInt(categoryIdData);
  const response = await categoryStore.removeCategoryById(categoryId);
  if(response.success) await getAllCategories()
}

onBeforeMount(() => {
  getAllCategories();
});

const editableData: UnwrapRef<Record<number, Partial<Category>>> = reactive({});

const edit = (key: string) => {
  editableData[key] = { ...categoryItems.value.find((item) => parseInt(key) === item.id) };
};

const save = (key: string) => {
  Object.assign(
      categoryItems.value.find(
          (item) => parseInt(key) === item.id
      ),
      editableData[parseInt(key)]
  );
  editCategoryById(editableData[parseInt(key)])
  delete editableData[parseInt(key)];
};

const cancel = (key: string) => {
  delete editableData[parseInt(key)];
};

const setModalVisible = (open: boolean) => {
  modalVisible.value = open;
};

const formStateManufacturer = reactive<Category>({
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
      const field = filter_options_categories.filter((item) => item.key == dataIndex)[0].req
      console.log(field, selectedKeys[0])
      getAllProductsByFieldName(field, selectedKeys[0])
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
  getAllCategories()
};


const handleOkButtonModal = () => {
  createCategory()
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
