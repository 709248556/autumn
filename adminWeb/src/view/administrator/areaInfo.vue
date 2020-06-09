<template>
  <TreeListManager ref="treeListManager"
                   :managerName="this.managerName"
                   :queryChildrenApi="this.queryChildrenApi"
                   :queryByIdApi="this.queryByIdApi"
                   :addApi="this.addApi"
                   :updateApi="this.updateApi"
                   :deleteApi="this.deleteApi"
                   :downloadByExcelInfoApi="this.downloadByExcelInfoApi"
                   :importInfo="this.getImportInfo()">
  </TreeListManager>
</template>
<script>
import TreeListManager from '@/components/autumn/tree-list-name-code-manager'
import {
  add,
  deleteById,
  queryById,
  update,
  queryChildren,
  downloadByExcelInfo,
  downloadImportTemplateByExcelInfo,
  dataImportByExcelUrl
} from '@/api/administrator/areaInfo'
export default {
  name: 'areaInfoManager',
  components: {
    TreeListManager
  },
  data () {
    return {
      managerName: '行政区',
      queryChildrenApi: queryChildren,
      queryByIdApi: queryById,
      addApi: add,
      updateApi: update,
      deleteApi: deleteById,
      downloadByExcelInfoApi: downloadByExcelInfo
    }
  },
  methods: {
    getImportInfo () {
      let _this = this
      let result = {
        uploadFileUrl: dataImportByExcelUrl,
        downloadTemplateApi: downloadImportTemplateByExcelInfo,
        importTitle: '行政区导入',
        refreshApi: function () {
          _this.$refs.treeListManager.onRefresh()
        }
      }
      return result
    }
  },
  mounted () {

  }
}
</script>
