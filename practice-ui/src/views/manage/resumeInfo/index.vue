<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="简历编号" prop="resumeId">
        <el-input
          v-model="queryParams.resumeId"
          placeholder="请输入简历编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="fullName">
        <el-input
          v-model="queryParams.fullName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="queryParams.gender" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
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
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker
          v-model="daterangeUpdateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['manage:resumeInfo:add']"
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
          v-hasPermi="['manage:resumeInfo:edit']"
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
          v-hasPermi="['manage:resumeInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:resumeInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="resumeInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="简历编号" align="center" v-if="columns[0].visible" prop="resumeId" />
        <el-table-column label="姓名" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="fullName" />
        <el-table-column label="联系电话" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="phone" />
        <el-table-column label="电子邮箱" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="email" />
        <el-table-column label="性别" align="center" v-if="columns[4].visible" prop="gender">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender"/>
        </template>
      </el-table-column>
        <el-table-column label="出生日期" align="center" v-if="columns[5].visible" prop="birthDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="地址" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="address" />
        <el-table-column label="求职意向" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="jobIntention" />
        <el-table-column label="教育经历" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="education" />
        <el-table-column label="工作经历" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible" prop="workExperience" />
        <el-table-column label="自我介绍" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="introduction" />
        <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="userId" />
        <el-table-column label="班级" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="deptId" />
        <el-table-column label="创建时间" align="center" v-if="columns[13].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible" prop="updateBy" />
        <el-table-column label="更新时间" align="center" v-if="columns[15].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:resumeInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:resumeInfo:remove']"
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

    <!-- 添加或修改简历信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="fullName">
          <el-input v-model="form.fullName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮箱" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker clearable
            v-model="form.birthDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="求职意向" prop="jobIntention">
          <el-input v-model="form.jobIntention" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="教育经历" prop="education">
          <el-input v-model="form.education" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="工作经历" prop="workExperience">
          <el-input v-model="form.workExperience" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自我介绍" prop="introduction">
          <el-input v-model="form.introduction" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="创建人" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="班级" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入班级" />
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
import { listResumeInfo, getResumeInfo, delResumeInfo, addResumeInfo, updateResumeInfo } from "@/api/manage/resumeInfo";

export default {
  name: "ResumeInfo",
  dicts: ['sys_user_sex'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '简历编号', visible: true },
          { key: 1, label: '姓名', visible: true },
          { key: 2, label: '联系电话', visible: true },
          { key: 3, label: '电子邮箱', visible: true },
          { key: 4, label: '性别', visible: true },
          { key: 5, label: '出生日期', visible: true },
          { key: 6, label: '地址', visible: true },
          { key: 7, label: '求职意向', visible: true },
          { key: 8, label: '教育经历', visible: true },
          { key: 9, label: '工作经历', visible: true },
          { key: 10, label: '自我介绍', visible: true },
          { key: 11, label: '创建人', visible: true },
          { key: 12, label: '班级', visible: true },
          { key: 13, label: '创建时间', visible: true },
          { key: 14, label: '更新人', visible: true },
          { key: 15, label: '更新时间', visible: true },
          { key: 16, label: '备注', visible: true },
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
      // 简历信息表格数据
      resumeInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeBirthDate: [],
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        resumeId: null,
        fullName: null,
        gender: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        fullName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "班级不能为空", trigger: "blur" }
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
    /** 查询简历信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeBirthDate && '' != this.daterangeBirthDate) {
        this.queryParams.params["beginBirthDate"] = this.daterangeBirthDate[0];
        this.queryParams.params["endBirthDate"] = this.daterangeBirthDate[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params["beginUpdateTime"] = this.daterangeUpdateTime[0];
        this.queryParams.params["endUpdateTime"] = this.daterangeUpdateTime[1];
      }
      listResumeInfo(this.queryParams).then(response => {
        this.resumeInfoList = response.rows;
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
        resumeId: null,
        fullName: null,
        phone: null,
        email: null,
        gender: null,
        birthDate: null,
        address: null,
        jobIntention: null,
        education: null,
        workExperience: null,
        introduction: null,
        userId: null,
        deptId: null,
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
      this.daterangeBirthDate = [];
      this.daterangeCreateTime = [];
      this.daterangeUpdateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.resumeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加简历信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const resumeId = row.resumeId || this.ids
      getResumeInfo(resumeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改简历信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.resumeId != null) {
            updateResumeInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addResumeInfo(this.form).then(response => {
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
      const resumeIds = row.resumeId || this.ids;
      this.$modal.confirm('是否确认删除简历信息编号为"' + resumeIds + '"的数据项？').then(function() {
        return delResumeInfo(resumeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/resumeInfo/export', {
        ...this.queryParams
      }, `resumeInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
