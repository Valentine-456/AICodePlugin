# AICodePlugin

## Test task
Create a simple AI-related IntelliJ plugin of your choice for IntelliJ Ultimate.

-----------------

## Features
This plugin allows you to select a code snippet and explain what it does using AI in the tool window.

1) Select text you would like to change and right-click on the selection:
2) Click the action "Explain Code Snippet" - the tool window will pop up with the explanation.
3) Doing right-click without text selection results in explaining the whole current file.
   
-------------

## Idea and Implementation

I developed this idea because my work experience motivated me to create a solution like this.
In production systems, legacy code is often difficult to understand and wiki pages are frequently outdated, leading to a constant need for onboarding meetings. 
Although simple, this project meets the requirements and demonstrates my ability to work with AI tools.

------------
### Further improvement ideas:

- Add markdown support + better UI
- Add traversal of project/PSI of the usage of the tokens/identifiers that are within the text selection scope.
- Adding integration of Wiki pages and documentation within RAG/MCP for better explanation of legacy/private code.
- Add actions that will correct (based on Wiki pages and documentation) the selected code, using llm.

------------

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "AICodePlugin"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/Valentine-456/AICodePlugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
