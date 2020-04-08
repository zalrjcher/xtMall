import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login'
import Register from '@/components/register'
import  Index from '@/components/index'
import Mall from '@/components/mall'
import MallDetailed from '@/components/mallPage/mallDetailed'
import MallIndex from '@/components/mallIndex'
import Person from '@/components/personPage/person'
import PersonMessage from '@/components/personPage/personMessage'
import InspectGood from '@/components/personPage/inspectGood'
import InspectComplaint from '@/components/personPage/inspectComplaint'
import Address from '@/components/personPage/address'
import MyGood from '@/components/personPage/myGood'
import PersonBill from '@/components/personPage/personBill'
import Discount from '@/components/personPage/discount'
import EditGood from '@/components/personPage/editGood'
import ReGoodBill from '@/components/personPage/reGoodBill'
import EvaBill from '@/components/personPage/EvaBill'
import UserManage from '@/components/personPage/userManage'
import AllGood from '@/components/mallPage/allGood'
import BuyPage from '@/components/util/BuyPage'
import ShopCart from '@/components/mallPage/shopCart'
Vue.use(Router)

export default new Router({
  routes: [
    {path: '*', redirect: '/mallIndex'},
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },

    {
      path: '/editGood',
      name: 'EditGood',
      component: EditGood,
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/index',
      name: 'Index',
      component: Index,
      children:[
        {
          path: '/mall',
          component: Mall,
          mate:{
            requireAuth:true
          },
          children: [
             {
              path: '/shopCart',
              name: 'ShopCart',
              component: ShopCart,
              mate:{
                requireAuth:true
              }},
            {
              path: '/buyPage',
              name: 'BuyPage',
              component: BuyPage,
              mate:{
                requireAuth:true
              }
            },
            {
              path: '/allGood',
              name: 'AllGood',
              component: AllGood,
              mate:{
                requireAuth:true
              }
            },
            {
              path: '/mallDetailed',
              name: 'MallDetailed',
              component: MallDetailed,
              mate:{
                requireAuth:true
              }
            },
            {
              path: '/mallIndex',
              name: 'MallIndex',
              component: MallIndex,
              mate:{
                requireAuth:true
              }
            }
          ]
        },
        {
          path: '/person',
          name: 'Person',
          component: Person,
          children:[
            {
              path: '/inspectGood',
              name: 'InspectGood',
              component: InspectGood,
            },
            {
              path: '/inspectComplaint',
              name: 'InspectComplaint',
              component: InspectComplaint,
            },
            {
              path: '/personMessage',
              name: 'PersonMessage',
              component: PersonMessage,
            },
            {
              path: '/userManage',
              name: 'UserManage',
              component: UserManage,
            },
            {
              path: '/address',
              name: 'Address',
              component: Address,
            },
            {
              path: '/discount',
              name: 'Discount',
              component: Discount,
            },
            {
              path: '/myGood',
              name: 'MyGood',
              component: MyGood,
            },
            {
              path: '/personBill',
              name: 'PersonBill',
              component: PersonBill,
            },
            {
              path: '/reGoodBill',
              name: 'ReGoodBill',
              component: ReGoodBill,
            },
            {
              path: '/evaBill',
              name: 'EvaBill',
              component: EvaBill,
            },
          ]
        }
      ]
    },

  ]
})
