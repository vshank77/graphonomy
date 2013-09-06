define([ 'jquery', 'lodash', 'backbone', 'vm', 'text!templates/page.html', 'views/sidemenu',
        'views/section' ], 
function($, _, Backbone, Vm, backbonePageTemplate, SidemenuView, SectionView) {
    var BackbonePage = Backbone.View.extend({
        el : '.page',
        render : function() {
            this.$el.html(backbonePageTemplate);

            var sidemenuView = Vm.create(this, 'BackboneSideMenuView', SidemenuView);
            sidemenuView.render();

            var sectionView = Vm.create(this, 'BackboneSectionView', SectionView, {
                section : this.options.section
            });
            sectionView.render();
        }
    });

    return BackbonePage;
});