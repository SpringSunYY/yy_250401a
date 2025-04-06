<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请编号" prop="applyId">
        <el-input
          v-model="queryParams.applyId"
          placeholder="请输入申请编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="applyStatus">
        <el-select v-model="queryParams.applyStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.m_common_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="创建人" prop="userId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.userId"-->
      <!--          placeholder="请输入创建人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="班级" prop="deptId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.deptId"-->
      <!--          placeholder="请输入班级"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
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
      <!--      <el-form-item label="更新人" prop="updateBy">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.updateBy"-->
      <!--          placeholder="请输入更新人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="更新时间">-->
      <!--        <el-date-picker-->
      <!--          v-model="daterangeUpdateTime"-->
      <!--          style="width: 240px"-->
      <!--          value-format="yyyy-MM-dd"-->
      <!--          type="daterange"-->
      <!--          range-separator="-"-->
      <!--          start-placeholder="开始日期"-->
      <!--          end-placeholder="结束日期"-->
      <!--        ></el-date-picker>-->
      <!--      </el-form-item>-->
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
          v-hasPermi="['manage:postApplyInfo:add']"
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
          v-hasPermi="['manage:postApplyInfo:edit']"
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
          v-hasPermi="['manage:postApplyInfo:remove']"
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
          v-hasPermi="['manage:postApplyInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="postApplyInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="申请编号" align="center" v-if="columns[0].visible" prop="applyId"/>
      <el-table-column label="公司编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="companyName"
      />
      <el-table-column label="简历编号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="resumeName"
      />
      <el-table-column label="岗位编号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="postName"
      />
      <el-table-column label="状态" align="center" v-if="columns[4].visible" prop="applyStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.m_common_status" :value="scope.row.applyStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="userName"
      />
      <el-table-column label="班级" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="deptName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[7].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[9].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-printer"
            @click="openPrint(scope.row)"
            v-hasPermi="['manage:postApplyInfo:query']"
          >打印
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAddAssessment(scope.row)"
            v-if="scope.row.applyStatus==='1'"
            v-hasPermi="['manage:monthlyAssessmentInfo:add']"
          >考核
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:postApplyInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:postApplyInfo:remove']"
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

    <!-- 添加或修改岗位申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="公司编号" prop="companyId">-->
        <!--          <el-input v-model="form.companyId" placeholder="请输入公司编号"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="简历编号" prop="resumeId">
          <el-select
            v-model="form.resumeId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入简历名字"
            :remote-method="selectResumeInfoList"
            :loading="resumeLoading"
          >
            <el-option
              v-for="item in resumeInfoList"
              :key="item.resumeId"
              :label="item.resumeId+'---'+item.fullName"
              :value="item.resumeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="岗位编号" prop="postId">
          <el-select
            v-model="form.postId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入岗位名字"
            :remote-method="selectPostInfoList"
            :loading="postLoading"
          >
            <el-option
              v-for="item in postInfoList"
              :key="item.postId"
              :label="item.postId+'---'+item.postName"
              :value="item.postId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" v-hasPermi="['manage:postApplyInfo:audit']" prop="applyStatus">
          <el-radio-group v-model="form.applyStatus">
            <el-radio
              v-for="dict in dict.type.m_common_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!--        <el-form-item label="创建人" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入创建人"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="班级" prop="deptId">-->
        <!--          <el-input v-model="form.deptId" placeholder="请输入班级"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="打印简历"
      :visible.sync="printVisible"
      width="800px"
      append-to-body
    >
      <PrintResume :resume="printResume"/>

      <span slot="footer" class="dialog-footer">
        <el-button @click="printVisible = false">取 消</el-button>
        <el-button type="primary" v-print="'#print-resume'">打 印</el-button>
      </span>
    </el-dialog>

    <!-- 添加或修改月度考核对话框 -->
    <el-dialog :title="title" :visible.sync="openAssessment" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核年份" prop="assessYear">
          <el-input-number :min="2024" v-model="form.assessYear" placeholder="请输入考核年份"/>
        </el-form-item>
        <el-form-item label="考核月份" prop="assessMonth">
          <el-input-number :min="1" :max="12" v-model="form.assessMonth" placeholder="请输入考核月份"/>
        </el-form-item>
        <el-form-item label="考核得分" prop="score">
          <el-input-number :min="0" :max="100" v-model="form.score" placeholder="请输入考核得分"/>
        </el-form-item>
        <el-form-item label="评语" prop="comments">
          <el-input v-model="form.comments" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAssessmentForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPostApplyInfo,
  getPostApplyInfo,
  delPostApplyInfo,
  addPostApplyInfo,
  updatePostApplyInfo
} from '@/api/manage/postApplyInfo'
import { getResumeInfo, listResumeInfo } from '@/api/manage/resumeInfo'
import { listPostInfo } from '@/api/manage/postInfo'
import PrintResume from '@/components/PrintResume/index.vue'
import { addMonthlyAssessmentInfo } from '@/api/manage/monthlyAssessmentInfo'

export default {
  name: 'PostApplyInfo',
  components: { PrintResume },
  dicts: ['m_common_status'],
  data() {
    return {
      openAssessment: false,
      printVisible: false, // 控制是否渲染打印组件
      printResume: {},     // 当前打印的简历数据
      //简历相关信息
      resumeInfoList: [],
      resumeLoading: false,
      resumeQueryParams: {
        fullName: '',
        pageNum: 1,
        pageSize: 100
      },
      //岗位相关信息
      postInfoList: [],
      postLoading: false,
      postQueryParams: {
        postName: '',
        pageNum: 1,
        pageSize: 100,
        postStatus: '1'
      },
      //表格展示列
      columns: [
        { key: 0, label: '申请编号', visible: false },
        { key: 1, label: '公司编号', visible: true },
        { key: 2, label: '简历编号', visible: true },
        { key: 3, label: '岗位编号', visible: true },
        { key: 4, label: '状态', visible: true },
        { key: 5, label: '创建人', visible: true },
        { key: 6, label: '班级', visible: false },
        { key: 7, label: '创建时间', visible: true },
        { key: 8, label: '更新人', visible: false },
        { key: 9, label: '更新时间', visible: false },
        { key: 10, label: '备注', visible: false }
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
      // 岗位申请表格数据
      postApplyInfoList: [],
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
        applyId: null,
        applyStatus: null,
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
        companyId: [
          { required: true, message: '公司编号不能为空', trigger: 'blur' }
        ],
        resumeId: [
          { required: true, message: '简历编号不能为空', trigger: 'blur' }
        ],
        postId: [
          { required: true, message: '岗位编号不能为空', trigger: 'blur' }
        ],
        applyStatus: [
          { required: false, message: '状态不能为空', trigger: 'change' }
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
    this.getResumeInfoList()
    this.getPostInfoList()
  },
  methods: {
    //提交月考核
    submitAssessmentForm() {
      addMonthlyAssessmentInfo(this.form).then(res => {
        this.$modal.msgSuccess('新增成功')
        this.openAssessment = false
      })
    },
    //打开月度考核对话框
    handleAddAssessment(row) {
      this.reset()
      this.form.companyId = row.companyId
      this.form.userId = row.userId
      this.title = '月度考核'
      this.openAssessment = true
    },
    openPrint(row) {
      getResumeInfo(row.resumeId).then(response => {
        this.printResume = response.data
        this.printVisible = true
      })
    },
    /**
     * 获取岗位列表推荐
     * @param query
     */
    selectPostInfoList(query) {
      if (query !== '') {
        this.postLoading = true
        this.postQueryParams.postName = query
        setTimeout(() => {
          this.getPostInfoList()
        }, 200)
      } else {
        this.postInfoList = []
        this.postQueryParams.postName = null
      }
    }
    ,
    /**
     * 获取岗位信息列表
     */
    getPostInfoList() {
      //添加查询参数
      if (this.form.postId != null) {
        this.postQueryParams.postId = this.form.postId
      } else {
        this.postQueryParams.postId = null
      }
      if (this.postQueryParams.postName !== '') {
        this.postQueryParams.postId = null
      }
      this.postInfoList = []
      listPostInfo(this.postQueryParams).then(res => {
        this.postInfoList = res?.rows
        this.postLoading = false
      })
    },
    /**
     * 获取简历列表推荐
     * @param query
     */
    selectResumeInfoList(query) {
      if (query !== '') {
        this.resumeLoading = true
        this.resumeQueryParams.fullName = query
        setTimeout(() => {
          this.getResumeInfoList()
        }, 200)
      } else {
        this.resumeInfoList = []
        this.resumeQueryParams.fullName = null
      }
    }
    ,
    /**
     * 获取简历信息列表
     */
    getResumeInfoList() {
      //添加查询参数
      if (this.form.resumeId != null) {
        this.resumeQueryParams.resumeId = this.form.resumeId
      } else {
        this.resumeQueryParams.resumeId = null
      }
      if (this.resumeQueryParams.userName !== '') {
        this.resumeQueryParams.resumeId = null
      }
      this.resumeInfoList = []
      listResumeInfo(this.resumeQueryParams).then(res => {
        this.resumeInfoList = res?.rows
        this.resumeLoading = false
      })
    },
    /** 查询岗位申请列表 */
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
      listPostApplyInfo(this.queryParams).then(response => {
        this.postApplyInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openAssessment = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        applyId: null,
        companyId: null,
        resumeId: null,
        postId: null,
        applyStatus: null,
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
      this.ids = selection.map(item => item.applyId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加岗位申请'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const applyId = row.applyId || this.ids
      getPostApplyInfo(applyId).then(response => {
        this.form = response.data
        this.getResumeInfoList()
        this.getPostInfoList()
        this.open = true
        this.title = '修改岗位申请'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.applyId != null) {
            updatePostApplyInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPostApplyInfo(this.form).then(response => {
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
      const applyIds = row.applyId || this.ids
      this.$modal.confirm('是否确认删除岗位申请编号为"' + applyIds + '"的数据项？').then(function() {
        return delPostApplyInfo(applyIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/postApplyInfo/export', {
        ...this.queryParams
      }, `postApplyInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
