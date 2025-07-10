import Link from "next/link";
import "../css/nav.css";

export default function Navigation() {
  return (
    <nav className="main-nav">
      <Link href="/" className="logo">
        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="24" viewBox="0 0 24 24">
          <path d="M12 2A3 3 0 1 0 12 8 3 3 0 1 0 12 2z"></path>
          <path d="M19 5A3 3 0 1 0 19 11 3 3 0 1 0 19 5z"></path>
          <path d="M5 5A3 3 0 1 0 5 11 3 3 0 1 0 5 5z"></path>
          <path d="m8.06,11.53c-.37.37-.84.63-1.35.76-2.18.55-3.71,2.5-3.71,4.75,0,1.66.83,3.19,2.22,4.1.81.53,1.74.8,2.68.8.67,0,1.33-.14,1.96-.41l.98-.43c.74-.32,1.58-.32,2.32,0l.98.43c1.52.66,3.25.52,4.64-.39,1.39-.91,2.22-2.44,2.22-4.1,0-2.25-1.53-4.2-3.71-4.75-.51-.13-.97-.39-1.35-.76l-.48-.48c-.92-.92-2.15-1.43-3.46-1.43s-2.54.51-3.46,1.43l-.48.48Z"></path>
        </svg>
        <span className="brand-name">
          Animal<span>Pedia</span>
        </span>
      </Link>

      <ul className="nav-links">
        <li>
          <Link href="/">Home</Link>
        </li>
        <li>
          <Link href="/animals/all">Animals</Link>
        </li>
        <li>
          <div className="list-dropdown-toggle" role="button">
            <span>Animals List</span>

            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="14"
              fill="#2C2C2C"
              viewBox="0 0 24 24"
            >
              <path d="M17.35 8H6.65c-.64 0-.99.76-.56 1.24l5.35 6.11c.3.34.83.34 1.13 0l5.35-6.11C18.34 8.76 18 8 17.36 8Z"></path>
            </svg>

            <ul className="animals-list">
              <li>
                <Link href="/animals/mammals">Mammals</Link>
              </li>
              <li>
                <Link href="/animals/birds">Birds</Link>
              </li>
              <li>
                <Link href="/animals/reptiles">Reptiles</Link>
              </li>
              <li>
                <Link href="/animals/amphibians">Amphibians</Link>
              </li>
              <li>
                <Link href="/animals/fish">Fish</Link>
              </li>
            </ul>
          </div>
        </li>
        <li>
          <Link href="/register">Register</Link>
        </li>
        <li>
          <Link href="/login">Login</Link>
        </li>
        <li>
          <Link href="/about">About</Link>
        </li>
        <li>
          <form>
            <input type="search" placeholder="Search" aria-label="Search" name="search" />
            <svg
              className="search-icon"
              role="button"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="#2C2C2C"
              viewBox="0 0 24 24"
            >
              <path d="m18,10c0-4.41-3.59-8-8-8S2,5.59,2,10s3.59,8,8,8c1.85,0,3.54-.63,4.9-1.69l5.1,5.1,1.41-1.41-5.1-5.1c1.05-1.36,1.69-3.05,1.69-4.9Zm-14,0c0-3.31,2.69-6,6-6s6,2.69,6,6-2.69,6-6,6-6-2.69-6-6Z"></path>
            </svg>
          </form>
        </li>
      </ul>
    </nav>
  );
}
