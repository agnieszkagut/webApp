import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import SponsorshipListComponent from "./SponsorshipListComponent";
import AddSponsorshipsComponent from "./AddSponsorshipsComponent";

class SponsorshipComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            showSponsorships: false,
            showEdit: false
        }
    }
    render(){
        return(
            <div>
                <div>
                    {this.props.t('project.sponsorship', { framework: "react-i18next" })}{this.props.sponsorship}
                    {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                    ?<i className="fas fa-chevron-down" onClick={()=> this.setState({showSponsorships: !this.state.showSponsorships})}></i>
                    :null}
                </div>
                <div>
                    {this.state.showSponsorships
                        ?<SponsorshipListComponent listOfSponsorships={this.props.listOfSponsorships}
                                                   unassignedFunds={this.props.unassignedFunds}
                        />
                        :null
                    }
                </div>
                <div>
                    {
                        (this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                        ?<button className={this.props.buttonStyle} onClick={()=> this.setState({showEdit: !this.state.showEdit})}>
                            {this.props.t('buttons.edit', { framework: "react-i18next" })}
                        </button>
                        :null
                    }
                </div>
                <div>
                    {this.state.showEdit
                        ?<AddSponsorshipsComponent   unassignedFunds={this.props.unassignedFunds}
                                                     projectId={this.props.projectId}
                                                     buttonStyle={this.props.buttonStyle}
                                                     credentials={this.props.credentials}
                        />
                        :null
                    }
                </div>
            </div>
        )
    }
}

export default translate('common')(SponsorshipComponent);