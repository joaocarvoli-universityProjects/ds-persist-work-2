<template>

  <h2>Category</h2>

  <!-- Categories Table -->
  <Table :columns="columns_category" :data-source="categoryItems" bordered>
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
import { Table, Input, Typography, Popconfirm } from "ant-design-vue";
import { EditOutlined, DeleteOutlined, PlusOutlined } from "@ant-design/icons-vue";
import {columns_category} from "../utils/tablesCols.ts";
import {useCategoryStore} from "../stores/categoryStore.ts";
import {Category} from "../model/category.ts";

const categoryStore = useCategoryStore();
const categoryItems = ref<Category[]>([]);
const modalVisible = ref<boolean>(false);

async function getAllCategories() {
  const result = await categoryStore.getAllCategories();
  console.log(result)
  categoryItems.value = result;
  console.log(categoryItems.value)
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

const editableData: UnwrapRef<Record<string, Partial<Category>>> = reactive({});

const edit = (key: string) => {
  editableData[key] = { ...categoryItems.value.find((item) => key === item.id) };
};

const save = (key: string) => {
  Object.assign(
      categoryItems.value.find(
          (item) => key === item.id
      ),
      editableData[key]
  );
  editCategoryById(editableData[key])
  delete editableData[key];
};

const cancel = (key: string) => {
  delete editableData[key];
};

const setModalVisible = (open: boolean) => {
  modalVisible.value = open;
};

const formStateManufacturer = reactive<Category>({
  id: '',
  name: ''
});

const handleOkButtonModal = () => {
  createCategory()
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
