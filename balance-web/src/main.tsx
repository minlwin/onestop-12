import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter, Route, Routes } from 'react-router'
import AnonymousLayout from './pages/anonymous/_layout.tsx'
import SignIn from './pages/anonymous/signin.tsx'
import SignUp from './pages/anonymous/signup.tsx'
import AdminLayout from './pages/management/_layout.tsx'
import MembersLayout from './pages/members/_layout.tsx'
import DashBoard from './pages/management/dashboard.tsx'
import MemberManagement from './pages/management/member-management.tsx'
import MemberHome from './pages/members/member-home.tsx'
import LedgerManagement from './pages/members/ledger-management.tsx'
import LedgerEntryManagement from './pages/members/ledger-entry-management.tsx'
import BalanceManagement from './pages/members/balance-management.tsx'
import AuthResultContextProvider from './model/context/auth-result.context.provider.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthResultContextProvider>
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
          </Route>

          <Route path='/member' element={<MembersLayout />}>
            <Route index element={<MemberHome />} />
            <Route path='ledger' element={<LedgerManagement />} />
            <Route path='entry/:type' element={<LedgerEntryManagement />} />
            <Route path='balance' element={<BalanceManagement />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthResultContextProvider>
  </StrictMode>,
)
