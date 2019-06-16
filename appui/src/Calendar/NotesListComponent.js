import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import NoteComponent from "./NoteComponent";

class NotesListComponent extends Component {

    render(){
        return(
            <div>
                <ul>
                    {this.props.notes.map(function(note,index) {
                            return (
                                <div key={index}>
                                    <NoteComponent
                                        key={index}
                                        value={note.description}
                                    />
                                </div>
                            )
                        }
                    )}
                </ul>
            </div>
        )
    }
}

export default translate('common')(NotesListComponent);