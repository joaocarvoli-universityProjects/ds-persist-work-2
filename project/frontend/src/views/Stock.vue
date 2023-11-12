<template>
  <h2>Depósitos</h2>

  <!-- Manufacturer Table -->
  <Table :columns="columns_stock" :data-source="stockItems" bordered>
    <template #bodyCell="{ column, text, record }">
      <template v-if="['name', 'address', 'cep'].includes(column.dataIndex)">
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
              <Popconfirm title="Sure to delete?" @confirm="removeStockById(record.id)">
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
        <div>Cadastrar depósito</div>
      </template>
      <template #icon>
        <PlusOutlined />
      </template>
    </a-float-button>

    <a-modal v-model:open="modalVisible" title="Cadastrar depósito" centered @ok="handleOkButtonModal">
      <a-form>
        <div class="dates">
          <a-form-item
              label="Name"
              name="name">
            <a-input v-model:value="formStateManufacturer.name" />
          </a-form-item>
          <a-form-item
              label="CEP"
              name="cep">
            <a-input v-model:value="formStateManufacturer.cep" />
          </a-form-item>
        </div>

        <a-form-item
            label="Endereço"
            name="address">
          <a-input v-model:value="formStateManufacturer.address" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import {onBeforeMount, reactive, ref, UnwrapRef} from "vue";
import { Table, Input, Typography, Popconfirm } from "ant-design-vue";
import { Manufacturer } from "../model/manufacturer.ts";
import { EditOutlined, DeleteOutlined, PlusOutlined } from "@ant-design/icons-vue";
import {Stock} from "../model/stock.ts";
import {useStockStore} from "../stores/stockStore.ts";
import {columns_stock} from "../utils/tablesCols.ts";

const stockStore = useStockStore();
const stockItems = ref<Stock[]>([]);
const modalVisible = ref<boolean>(false);

async function getAllStocks() {
  const result = await stockStore.getAllStocks();
  stockItems.value = result;
}

async function createStock(){
  const response = await stockStore.createStock(formStateManufacturer)
  if(response.success) await getAllStocks()
}

async function editStockById(editableData: Record<string, Partial<Stock>>){
  const stock = (editableData as unknown) as Stock
  const response = await stockStore.editStockById(stock.id, stock);
  if(response.success) await getAllStocks()
}

async function removeStockById(stockIdData: string) {
  const stockId = parseInt(stockIdData);
  const response = await stockStore.removeStockById(stockId);
  if(response.success) await getAllStocks()
}

onBeforeMount(() => {
  getAllStocks();
});

const editableData: UnwrapRef<Record<number, Partial<Manufacturer>>> = reactive({});

const edit = (key: string) => {
  editableData[parseInt(key)] = { ...stockItems.value.find((item) => parseInt(key) === item.id) };
};

const save = (key: string) => {
  Object.assign(
      stockItems.value.find(
          (item) => parseInt(key) === item.id
      ),
      editableData[parseInt(key)]
  );
  console.log("field: " + editableData[parseInt(key)])
  editStockById(editableData[parseInt(key)])
  delete editableData[parseInt(key)];
};

const cancel = (key: string) => {
  delete editableData[parseInt(key)];
};

const setModalVisible = (open: boolean) => {
  modalVisible.value = open;
};

const formStateManufacturer = reactive<Stock>({
  id: 0,
  name: '',
  address: '',
  cep: ''
});

const handleOkButtonModal = () => {
  createStock()
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

.dates {
  display: flex;
  gap: 10px;
}
</style>
