<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司编号" prop="companyId">
        <el-input
          v-model="queryParams.companyId"
          placeholder="请输入公司编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司状态" prop="companyStatus">
        <el-select v-model="queryParams.companyStatus" placeholder="请选择公司状态" clearable>
          <el-option
            v-for="dict in dict.type.m_common_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:companyInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:companyInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:companyInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:companyInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="companyInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="公司编号" align="center" v-if="columns[0].visible" prop="companyId" />
        <el-table-column label="公司名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="companyName" />
        <el-table-column label="公司状态" align="center" v-if="columns[2].visible" prop="companyStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.m_common_status" :value="scope.row.companyStatus"/>
        </template>
      </el-table-column>
        <el-table-column label="公司地址" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="address" />
        <el-table-column label="联系电话" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible" prop="contactNumber" />
        <el-table-column label="公司邮箱" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="email" />
        <el-table-column label="公司简介" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="companyDesc" />
        <el-table-column label="公司图片" align="center" v-if="columns[7].visible" prop="companyImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.companyImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
        <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="userId" />
        <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="updateBy" />
        <el-table-column label="更新时间" align="center" v-if="columns[11].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:companyInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:companyInfo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改公司信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入公司名称" />
        </el-form-item>
        <el-form-item label="公司状态" prop="companyStatus">
          <el-radio-group v-model="form.companyStatus">
            <el-radio
              v-for="dict in dict.type.m_common_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="公司地址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="公司邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入公司邮箱" />
        </el-form-item>
        <el-form-item label="公司简介" prop="companyDesc">
          <el-input v-model="form.companyDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="公司图片" prop="companyImage">
          <image-upload v-model="form.companyImage"/>
        </el-form-item>
        <el-form-item label="创建人" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCompanyInfo, getCompanyInfo, delCompanyInfo, addCompanyInfo, updateCompanyInfo } from "@/api/manage/companyInfo";

export default {
  name: "CompanyInfo",
  dicts: ['m_common_status'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '公司编号', visible: true },
          { key: 1, label: '公司名称', visible: true },
          { key: 2, label: '公司状态', visible: true },
          { key: 3, label: '公司地址', visible: true },
          { key: 4, label: '联系电话', visible: true },
          { key: 5, label: '公司邮箱', visible: true },
          { key: 6, label: '公司简介', visible: true },
          { key: 7, label: '公司图片', visible: true },
          { key: 8, label: '创建人', visible: true },
          { key: 9, label: '创建时间', visible: true },
          { key: 10, label: '更新人', visible: true },
          { key: 11, label: '更新时间', visible: true },
          { key: 12, label: '备注', visible: true },
        ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 公司信息表格数据
      companyInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        companyName: null,
        companyStatus: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyName: [
          { required: true, message: "公司名称不能为空", trigger: "blur" }
        ],
        companyStatus: [
          { required: true, message: "公司状态不能为空", trigger: "change" }
        ],
        userId: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询公司信息列表 */
    getList() {
      this.loading = true;
      listCompanyInfo(this.queryParams).then(response => {
        this.companyInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        companyId: null,
        companyName: null,
        companyStatus: null,
        address: null,
        contactNumber: null,
        email: null,
        companyDesc: null,
        companyImage: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.companyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加公司信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const companyId = row.companyId || this.ids
      getCompanyInfo(companyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改公司信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.companyId != null) {
            updateCompanyInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCompanyInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const companyIds = row.companyId || this.ids;
      this.$modal.confirm('是否确认删除公司信息编号为"' + companyIds + '"的数据项？').then(function() {
        return delCompanyInfo(companyIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/companyInfo/export', {
        ...this.queryParams
      }, `companyInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
