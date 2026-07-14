import { Header } from './Header';

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex min-h-screen flex-col">
      <Header />
      <main className="mx-auto w-full max-w-6xl flex-1 px-4 py-6">{children}</main>
      <footer className="border-t border-gray-200 py-4 text-center text-xs text-gray-400">
        CardCric &copy; {new Date().getFullYear()}
      </footer>
    </div>
  );
}
