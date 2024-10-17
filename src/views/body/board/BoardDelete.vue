:root {
    --main-blue: #94C7FF;
    --white: #FFF;
    --black-60: #627086;
    --shadow-color: rgba(19, 24, 48, 0.15);
  }
  
  body {
    font-family: ABeeZee, sans-serif;
  }
  
  .layout {
    position: relative;
  }
  
  .sidebar {
    position: fixed;
    width: 20rem;
    height: 100vh;
    overflow-y: auto;
    transition: top 0.3s ease;
  }
  
  .sidebar, .tag-item button {
    border-radius: 7px;
    border: 0.5px solid var(--main-blue);
    background: var(--white);
  }
  
  .tag-item button {
    width: 146px;
    height: 30px;
    padding-top: 8px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: var(--black-60);
    font-size: 12px;
    font-style: italic;
    font-weight: 400;
    box-shadow: 0px 4px 8px 0px var(--shadow-color);
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
  }
  
  .tag-item button.active {
    background-color: var(--main-blue);
    color: var(--white);
  }
  
  .tag-list {
    list-style-type: none;
    padding: 0;
  }
  
  .tag-item {
    margin-bottom: 1rem;
  }
  
  .board-container {
    width: 100rem;
    margin: 30rem;
    margin-top: 8rem;
  }
  
  .board-card {
    background: var(--white);
    border: 0.0625rem solid #E0E0E0;
    border-radius: 1.5rem;
    padding: 1rem;
    margin: 1.5rem 0;
    box-shadow: 0px 1.5px 4.6px 0px rgba(0, 0, 0, 0.25);
  }
  
  .user-info {
    display: flex;
    align-items: center;
    margin-bottom: 0.75rem;
  }
  
  .profile-image {
    width: 5rem;
    height: 5rem;
    border-radius: 50%;
    margin-right: 0.75rem;
  }
  
  .nickname {
    font-weight: bold;
  }
  
  .board-title {
    font-size: 2rem;
    margin-bottom: 2rem;
  }
  
  .board-content {
    font-size: 2.3rem;
    margin-bottom: 0;
  }
  
  .image-container {
    display: flex;
    gap: 1.5rem;
    margin-bottom: 0.75rem;
  }
  
  .board-image {
    width: 30rem;
    height: 30rem;
    border-radius: 0.8rem;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    cursor: pointer;
  }
  
  .board-image:hover {
    transform: scale(1.05);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  }
  
  .board-image:active {
    transform: scale(1.1);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
  
  .board-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .interaction-count {
    font-size: 0.875rem;
    color: #666;
  }
  
  .more-button {
    background: none;
    border: none;
    color: #999;
    font-size: 1rem;
    cursor: pointer;
    padding: 0;
    margin-left: 0.25rem;
    text-decoration: none;
    transition: color 0.2s;
  }
  
  .more-button:hover {
    color: #555;
  }
  
  .loading {
    text-align: center;
    padding: 1.25rem;
    font-size: 1.125rem;
    color: #666;
  }