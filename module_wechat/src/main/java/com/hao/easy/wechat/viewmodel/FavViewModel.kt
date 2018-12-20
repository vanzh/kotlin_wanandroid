package com.hao.easy.wechat.viewmodel

import com.hao.easy.base.Config
import com.hao.easy.base.Config.isLogin
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wechat.Router
import com.hao.easy.wechat.model.Article
import com.hao.easy.wechat.repository.Api

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class FavViewModel : BaseArticleViewModel() {

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.getMyFav(page - 1).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }

    override fun cancelCollect(item: Article, position: Int) {
        if (!Config.isLogin) {
            Router.startLogin()
            return
        }
        Api.cancelCollect(item.originId).io_main().subscribeBy({
            invalidate()
        }, {

        }).add()
    }
}