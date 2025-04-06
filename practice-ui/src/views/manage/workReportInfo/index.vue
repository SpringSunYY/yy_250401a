<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报告编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入报告编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报告类型" prop="reportType">
        <el-select v-model="queryParams.reportType" placeholder="请选择报告类型" clearable>
          <el-option
            v-for="dict in dict.type.m_report_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报告日期" prop="reportDate">
        <el-date-picker clearable
                        v-model="queryParams.reportDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择报告日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="评分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入评分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入班级"
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
          v-hasPermi="['manage:workReportInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:workReportInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:workReportInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:workReportInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workReportInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="报告编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="报告类型" align="center" v-if="columns[1].visible" prop="reportType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.m_report_type" :value="scope.row.reportType"/>
        </template>
      </el-table-column>
      <el-table-column label="报告日期" align="center" v-if="columns[2].visible" prop="reportDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reportDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工作内容描述" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="content"
      />
      <el-table-column label="未来计划" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="plan"
      />
      <el-table-column label="遇到问题" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="problems"
      />
      <el-table-column label="评分" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="score"
      />
      <el-table-column label="评语" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="comment"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="userName"
      />
      <el-table-column label="班级" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="deptName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[10].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[12].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:workReportInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:workReportInfo:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改工作报告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报告类型" prop="reportType">
          <el-radio-group v-model="form.reportType">
            <el-radio
              v-for="dict in dict.type.m_report_type"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="报告日期" prop="reportDate">
          <el-date-picker clearable
                          v-model="form.reportDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择报告日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工作内容描述">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="未来计划" prop="plan">
          <el-input v-model="form.plan" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="遇到问题" prop="problems">
          <el-input v-model="form.problems" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="评分" v-has-permi="['manage:wordReportInfo:score']" prop="score">
          <el-input-number :min="0" :max="100" v-model="form.score" placeholder="请输入评分"/>
        </el-form-item>
        <el-form-item label="评语" v-has-permi="['manage:wordReportInfo:score']" prop="comment">
          <el-input v-model="form.comment" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {
  listWorkReportInfo,
  getWorkReportInfo,
  delWorkReportInfo,
  addWorkReportInfo,
  updateWorkReportInfo
} from '@/api/manage/workReportInfo'

export default {
  name: 'WorkReportInfo',
  dicts: ['m_report_type'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '报告编号', visible: false },
        { key: 1, label: '报告类型', visible: true },
        { key: 2, label: '报告日期', visible: true },
        { key: 3, label: '工作内容描述', visible: false },
        { key: 4, label: '未来计划', visible: false },
        { key: 5, label: '遇到问题', visible: false },
        { key: 6, label: '评分', visible: true },
        { key: 7, label: '评语', visible: true },
        { key: 8, label: '创建人', visible: true },
        { key: 9, label: '班级', visible: true },
        { key: 10, label: '创建时间', visible: false },
        { key: 11, label: '更新人', visible: false },
        { key: 12, label: '更新时间', visible: false },
        { key: 13, label: '备注', visible: false }
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
      // 工作报告表格数据
      workReportInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        reportType: null,
        reportDate: null,
        score: null,
        comment: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        reportType: [
          { required: true, message: '报告类型不能为空', trigger: 'change' }
        ],
        reportDate: [
          { required: true, message: '报告日期不能为空', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '工作内容描述不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '班级不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工作报告列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listWorkReportInfo(this.queryParams).then(response => {
        this.workReportInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        reportType: null,
        reportDate: null,
        content: null,
        plan: null,
        problems: null,
        score: null,
        comment: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加工作报告'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getWorkReportInfo(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改工作报告'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWorkReportInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addWorkReportInfo(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除工作报告编号为"' + ids + '"的数据项？').then(function() {
        return delWorkReportInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/workReportInfo/export', {
        ...this.queryParams
      }, `workReportInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
