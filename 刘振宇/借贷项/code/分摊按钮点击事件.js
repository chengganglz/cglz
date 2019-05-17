var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.eas.util.app);
imp.importPackage(Packages.com.kingdee.bos);
imp.importPackage(Packages.java.text);
imp.importPackage(Packages.java.lang);
imp.importPackage(Packages.com.kingdee.eas.custom.socketjk);
imp.importPackage(Packages.java.sql);
imp.importPackage(Packages.com.kingdee.bos.dao);
imp.importPackage(Packages.com.kingdee.eas.base.permission);
imp.importPackage(Packages.com.kingdee.eas.basedata.assistant);
imp.importPackage(Packages.com.kingdee.eas.basedata.master.cssp);
imp.importPackage(Packages.com.kingdee.eas.basedata.master.material);
imp.importPackage(Packages.com.kingdee.eas.basedata.org);
imp.importPackage(Packages.com.kingdee.eas.basedata.person);
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.common);
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.sd.sale);
imp.importPackage(Packages.com.kingdee.eas.common);
imp.importPackage(Packages.com.kingdee.eas.scm.common);
imp.importPackage(Packages.com.kingdee.eas.scm.sd.sale);
imp.importPackage(Packages.com.kingdee.eas.util);
imp.importPackage(Packages.com.kingdee.eas.util.app);
imp.importPackage(Packages.java.math);
imp.importPackage(Packages.java.util);
with (imp) {
    /**
     * 不进行四舍五入 保留两位小数
     * @param {number} num 要处理的数字
     * @param {number} decimal 要保留的小数位数
     */
    function formatDecimal(num, decimal) {
        num = num.toString()
        let index = num.indexOf('.')
        if (index !== -1) {
            num = num.substring(0, decimal + index + 1)
        } else {
            num = num.substring(0)
        }
        return parseFloat(num)
    }
    /**
     * 可分摊金额总数
     */
    var KF_sum = 0;
    /**
     * 出库单数量总数
     */
    var CKD_Num_sum = 0;
    /**
     * 出库单金额总数
     */
    var CKD_Money_sum = 0;
    /**
     * 出库单 分摊比例和
     */
    var CKD_FTBL_sum = 0;
    /**
     * 分摊类型
     * 0=按出库单数量
     * 1=按出库单金额
     * 2=手动指定分摊比例
     * 这里需要进行一下获取值
     */
    var FT_type = 0;
    console.log("分摊类型 这里需要改为获取值")
    /**
     * 分摊金额-尾差
     */
    var ftje_wc = 0;
    // 遍历借贷项调整单列表 获取本次分摊金额数量 
    for (var index = 0; index < array.length; index++) {
        // 借贷方向 这里需要改为获取值的方法
        var jdfx = 0;
        console.log("借贷方向 这里需要改为获取值")
        // 本次分摊金额
        var bcftje = 0;
        console.log("bcftje 这里需要改为获取值")
        // 判断 借贷方向 0=借，1=贷 
        if (jdfx == 0) {
            // 借向 为正值
            KF_sum += bcftje;
        } else if (jdfx == 1) {
            // 贷项 为负值
            KF_sum -= bcftje;
        }
        //因为只有这两个选择 不做另外处理
    }
    // 遍历出库单列表，获取出库单数量总数，出库单金额总数
    for (var index = 0; index < array.length; index++) {
        // 出库单数量 这里需要改为获取值的方法
        var ckd_num = 0;
        console.log("出库单数量 这里需要改为获取值")
        // 出库单金额 这里需要改为获取值的方法
        var ckd_money = 0;
        console.log("出库单金额 这里需要改为获取值")
        var ckd_ftbl = 0;
        console.log("出库单分摊比例 这里需要改为获取值")
        CKD_Money_sum += ckd_money;
        CKD_Num_sum += ckd_num;
        CKD_FTBL_sum += ckd_ftbl;
    }
    // 判断分摊方式是否为 手工指定比例分摊
    if (FT_type == 2) {
        // 校验 录入分摊比例的和是否为 100% 即 1
        if (CKD_FTBL_sum === 1) {

        } else {
            // 如果不是1 的话，弹错，退出
            console.log("如果不是1 的话，弹错，退出")
        }
    }
    /** 
     * 影响后总金额
     */
    var temp_ckd_yxhzje = 0;
    // 遍历出库单列表 进行值计算与反写
    for (var index = 0; index < array.length; index++) {
        /**
         * 出库单单价 这里需要改为获取值
         */
        var temp_ckd_dj = 0;
        console.log("出库单单价 这里需要改为获取值")
        /**
         * 出库单金额 这里需要改为获取值
         */
        var temp_ckd_money = 0;
        console.log("出库单金额 这里需要改为获取值")
        /**
         * 出库单数量 这里需要改为获取值
         */
        var temp_ckd_num = 0;
        console.log("出库单数量 这里需要改为获取值")
        /**
         * 数量占比 
         * */
        var temp_bili = 0;
        //需要根据不同的分摊类型做不同的处理
        switch (FT_type) {
            case 0: {
                // 按照出库单数量进行分摊
                temp_bili = temp_ckd_num / CKD_Num_sum;
            }
                break;
            case 1: {
                // 按照出库单金额进行分摊
                temp_bili = temp_ckd_money / CKD_Money_sum;
            }
                break;
            case 2: {
                // 按照手工指定比例进行分摊
                console.log("这里需要手动改为获取比例 这里需要改为获取值")
                temp_bili = 1;
            } break;
            default:
                break;
        }
        /** 
         * 根据数量比 获取所占总分摊金额
         * */
        var temp_ckd_ftzje = KF_sum * temp_bili;
        /**
         * 单价的影响值
         * 用可用总分摊金额/数量 不进行四舍五入，保留两位小数
         */
        var temp_dj_yxz = formatDecimal(temp_ckd_ftzje / temp_ckd_num, 2)
        // 计算 影响后单价
        temp_ckd_dj = temp_ckd_dj + temp_dj_yxz;

        // 计算影响后的金额
        temp_ckd_money = formatDecimal(temp_ckd_dj * temp_ckd_num, 2)


        // 将影响后金额 加入本次影响后总金额
        temp_ckd_yxhzje += temp_ckd_money;
        // 判断本次是否是最后一次，即当前行是否是最后一行
        if (index == rowcount - 1) {
            // 把尾差加到金额上
            // 计算尾差 恒等式 可分摊金额 + 出库单总金额 = 影响后出库单总金额 + 尾差
            ftje_wc = KF_sum + CKD_Money_sum - temp_ckd_yxhzje;
            temp_ckd_money += ftje_wc;
            // 再根据受影响的金额 / 数量 得到单价 尽量 均衡一点
            temp_ckd_dj = formatDecimal(temp_ckd_money / temp_ckd_num,2)
            // 但是这里存在问题 金额加上尾差后 单价怎么处理？ 可能会出现这种情况 数量 = 9 单价 = 10.01 但是金额 = 90.12
            console.log("Problem-但是这里存在问题 金额加上尾差后 单价怎么处理？")
        }
        // 将影响后的金额 写回
        console.log("将影响后的金额 写回")
        // 将影响后的单价 写回
        console.log("将影响后的单价 写回")

    }

}