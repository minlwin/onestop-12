import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter, Route, Routes } from 'react-router'
import AnonymousLayout from './pages/anonymous/_layout.tsx'
import SignIn from './pages/anonymous/signin.tsx'
import SignUp from './pages/anonymous/signup.tsx'
import AdminLayout from './pages/management/_layout.tsx'
import MembersLayout from './pages/members/_layout.tsx'
import DashBoard from './pages/management/dashboard/dashboard.tsx'
import MemberManagement from './pages/management/member/list.tsx'
import MemberHome from './pages/members/dashboard/dashboard.tsx'
import LedgerManagement from './pages/members/ledger/list.tsx'
import LedgerEntryManagement from './pages/members/entry/list.tsx'
import BalanceManagement from './pages/members/reporting/balance.tsx'
import Subscriptions from './pages/management/subscription/list.tsx'
import SubscriptionPlanManagement from './pages/management/master/plans/plans.tsx'
import PaymentMethods from './pages/management/master/payments/payments.tsx'
import EditSubscriptionPlan from './pages/management/master/plans/plan-edit.tsx'
import SubscriptionPlanDetails from './pages/management/master/plans/plan-details.tsx'
import EditPaymentMethod from './pages/management/master/payments/payment-edit.tsx'
import PaymentMethodDetails from './pages/management/master/payments/payment-details.tsx'
import SubscriptionDetails from './pages/management/subscription/details.tsx'
import LedgerEntryEdit from './pages/members/entry/edit.tsx'
import LedgerEntryDetails from './pages/members/reporting/details.tsx'
import MemberSubscriptions from './pages/members/subscriptions/list.tsx'
import SubscriptionApplication from './pages/members/subscriptions/edit.tsx'
import MemberSubscriptionDetails from './pages/members/subscriptions/details.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<AnonymousLayout />}>
          <Route index element={<SignIn />} />
          <Route path='signin' element={<SignIn />} />
          <Route path='signup' element={<SignUp />} />
        </Route>

        <Route path='/admin' element={<AdminLayout />}>
          <Route index element={<DashBoard />} />
          <Route path='members' element={<MemberManagement />} />
          <Route path='subscriptions' element={<Subscriptions />} />
          <Route path='subscriptions/:code' element={<SubscriptionDetails />} />
          <Route path='master/plan' element={<SubscriptionPlanManagement />} />
          <Route path='master/plan/edit' element={<EditSubscriptionPlan />} />
          <Route path='master/plan/:planId' element={<SubscriptionPlanDetails />} />
          <Route path='master/payment' element={<PaymentMethods />} />
          <Route path='master/payment/edit' element={<EditPaymentMethod />} />
          <Route path='master/payment/:paymentId' element={<PaymentMethodDetails />} />
        </Route>

        <Route path='/member' element={<MembersLayout />}>
          <Route index element={<MemberHome />} />
          <Route path='ledger' element={<LedgerManagement />} />
          <Route path='entry/:type' element={<LedgerEntryManagement />} />
          <Route path='entry/:type/edit' element={<LedgerEntryEdit />} />
          <Route path='balance' element={<BalanceManagement />} />
          <Route path='balance/:id' element={<LedgerEntryDetails />} />
          <Route path='subscription' element={<MemberSubscriptions />} />
          <Route path='subscription/:planId' element={<SubscriptionApplication />} />
          <Route path='subscription/details/:code' element={<MemberSubscriptionDetails />} />
        </Route>
      </Routes>
    </BrowserRouter>   
  </StrictMode>,
)
